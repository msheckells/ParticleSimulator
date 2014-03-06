import java.awt.Color;


public class Particle 
{
	private double MAX_VEL = 8000;
	private double m_x;
	private double m_y;
	private double m_xb;
	private double m_yb;
	private double m_vx;
	private double m_vy;
	private double m_mass;
	private int m_ID;
	private Color m_c;
	
	Particle(int id, double x, double y, double vx0, double vy0, double xb, double yb, double mass, Color color)
	{
		m_ID = id;
		m_x = x;
		m_y = y;
		m_vx = vx0;
		m_vy = vy0;
		m_xb = xb;
		m_yb = yb;
		m_mass = mass;
		m_c= color;
	}
	
	Color GetColor()
	{
		return m_c;
	}
	
	double getV()
	{
		return Math.sqrt(m_vx*m_vx + m_vy*m_vy);
	}
	
	int GetID()
	{
		return m_ID;
	}
	
	void SetVelocity(double vx, double vy)
	{
		m_vx = vx;
		m_vy = vy;
	}
	
	void UpdatePositionVelocity(double fx, double fy, double dt)
	{
		if(m_x < 0)
		{
			m_vx = Math.abs(m_vx);
		}
		else if(m_x >= m_xb)
		{
			m_vx = -Math.abs(m_vx);
		}
		if(m_y < 0)
		{
			m_vy = Math.abs(m_vy);
		}
		else if(m_y >= m_yb)
		{
			m_vy = -Math.abs(m_vy);
		}
		
		m_x += m_vx*dt;
		m_y += m_vy*dt;
		m_vx += dt*fx/m_mass;
		m_vy += dt*fy/m_mass;
		double vel = Math.sqrt(Math.pow(m_vx,2)+Math.pow(m_vy,2));
		if(vel > MAX_VEL)
		{
			m_vx = m_vx*MAX_VEL/vel;
			m_vy = m_vy*MAX_VEL/vel;
		}
		
	}
	
	double GetX()
	{
		return m_x;
	}
	
	double GetY()
	{
		return m_y;
	}
	
	double GetVX()
	{
		return m_vx;
	}
	
	double GetVY()
	{
		return m_vy;
	}
	
	double GetMass()
	{
		return m_mass;
	}

}
