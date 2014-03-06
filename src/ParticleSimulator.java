import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;


public class ParticleSimulator  extends Simulation
{
	ArrayList<Particle> m_particles;
	private static final double G = 1000000;
	private static final double K = 900000;
	private static final int xsize = 800;
	private static final int ysize = 800;
	private static final int xb = 700;
	private static final int yb = 700;
	private static final int initv = 1000;
	private static final int max_mass = 10;
	int radiusMax = 4;
	private long lastAddTime;
	private int nextID;
	private double[][] forces;
	private int numParticles;
	private Color[] colors = {Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.GRAY, Color.WHITE};
	
	

	
	ParticleSimulator()
	{
		nextID = 0;
		numParticles = 0;
		m_particles = new ArrayList<Particle>();
		//m_particles.add(new Particle(300, 300,0,0, xsize, ysize, 5));
		//m_particles.add(new Particle(200, 200,0,0, xsize, ysize, 5));
		AddRandomParticle(100);
		lastAddTime = System.nanoTime();
	}
	
	private void AddRandomParticle(int num)
	{
		for(int i = 0; i < num; i++)
		{
			m_particles.add(new Particle(nextID, Math.random()*xb + (xsize - xb)/2, Math.random()*yb + (ysize - xb)/2, 
				Math.random()*initv - initv/2, Math.random()*initv - initv/2, xsize, ysize, Math.random()*(max_mass-1)+1, colors[(int) Math.round(Math.random()*(colors.length-1))]));
			nextID++;
		}
		numParticles += num;
	}
	
	public void Update(double dt, Graphics g)
	{
		if((System.nanoTime() - lastAddTime)/1000000000 > 1)
		{
			//AddRandomParticle(1);
			lastAddTime = System.nanoTime();
		}
		UpdateParticles(dt);
		if(g != null)
		{
			paint(g);
		}
	}
	
	private void UpdateParticles(double dt)
	{
		/**
		for(Particle p : m_particles)
		{
			System.out.println(p.GetX() + " " + p.GetY());
		}
		/**/
		forces = new double[numParticles][numParticles];
		for(Particle p : m_particles)
		{
			double fx = 0;
			double fy = 0;
			double px = p.GetX();
			double py = p.GetY();
			double vx = p.GetVX();
			double vy = p.GetVY();
			double radius = (int)(p.GetMass()/max_mass * radiusMax);
			for(Particle po : m_particles)
			{
				double pox = po.GetX();
				double poy = po.GetY();
				double radius2 = (int)(po.GetMass()/max_mass * radiusMax);
				
				if(po != p)
				{
					double d = Math.sqrt(Math.pow(poy-py,2)+Math.pow(pox-px,2));
					double theta = Math.atan((poy-py)/(pox-px));
					double gf = (G*p.GetMass()*po.GetMass()/Math.pow(d,2));
					double ef = (K/Math.pow(d-radius-radius2,2));
					
					fx += Math.signum(pox-px)*(gf-ef)*Math.cos(theta);
					fy += Math.signum(pox-px)*(gf-ef)*Math.sin(theta);
				}
			}
			double drag = 50.0;
			fx -= drag*vx;
			fy -= drag*vy;
			//System.out.println(fx + " " + fy);
			p.UpdatePositionVelocity(fx, fy, dt);
		}
	}
	
	public void paint(Graphics g)
	{

		double lenMult = .01;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, xsize, ysize);
		
		for(Particle p : m_particles)
		{
			g.setColor(p.GetColor());
			int radius = (int)(p.GetMass()/max_mass * radiusMax);
			g.fillOval((int)p.GetX()-radius, (int)p.GetY()-radius, radius*2, radius*2);
			//System.out.println(p.GetX());
			//g.drawLine((int)p.GetX(), (int)p.GetY(), (int)(p.GetX() - p.GetVX()*lenMult), (int)(p.GetY() - p.GetVY()*lenMult));
		}
	}

}
