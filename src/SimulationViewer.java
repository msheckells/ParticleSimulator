import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class SimulationViewer implements KeyListener
{
	public static final int ESC = 27;
	private static DisplayMode[] BEST_DISPLAY_MODES = new DisplayMode[] {
		new DisplayMode(800, 600, 32, 0),
        new DisplayMode(800, 600, 16, 0),
        new DisplayMode(800, 600, 8, 0),
        new DisplayMode(640, 480, 32, 0),
        new DisplayMode(640, 480, 16, 0),
        new DisplayMode(640, 480, 8, 0),
        new DisplayMode(1280, 720, 32, 0),
        new DisplayMode(1280, 720, 16, 0),
        new DisplayMode(1280, 720, 8, 0),
        new DisplayMode(1366, 768, 32, 0),
    };
	ScreenManager m_s;
	Simulation m_sim;
	
	SimulationViewer(Simulation s)
	{
		m_sim = s;
		m_s = new ScreenManager();
		
		DisplayMode dm = m_s.findFirstCompatibleMode(BEST_DISPLAY_MODES);
		m_s.setFullScreen(dm);
		m_s.getFullScreenWindow().addKeyListener(this);
		SetInvisibleCursor();
	}
	private void SetInvisibleCursor()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		 Cursor invisCursor = tk.createCustomCursor(tk.createImage(""),new Point(),null);
	     m_s.getFullScreenWindow().setCursor(invisCursor);

	}
	
	public void Run()
	{
		while(true)
		{
			m_sim.Update(.0001, m_s.getGraphics());
			m_s.update();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode()==ESC)
		{
			m_s.restoreScreen();
			System.exit(0);
		}
	}
	
	public static void main(String[] args)
	{
		Simulation s = new ParticleSimulator();
		SimulationViewer sv = new SimulationViewer(s);
		sv.Run();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
