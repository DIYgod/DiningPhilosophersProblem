class Philosopher extends Thread
{
	private int thinkingtimes = 0;
	private int eatingtimes = 0;
	int id;
	static int currentid = 0;
	private Forks fork;
	public Philosopher(Forks fork)
	{
		super();
		id = currentid;
		currentid++;
		this.fork = fork;
	}
	public void run()
	{
		while(true)
		{
			thinking();
			fork.takeFork();
			eating();
			fork.putFork();
		}
	}
	private void thinking()
	{
		thinkingtimes++;
		System.out.println("Philosopher " + id + " : thinking start!  Times : " + thinkingtimes);
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void eating()
	{
		eatingtimes++;
		System.out.println("Philosopher " + id + " : eating start!  Times : " + eatingtimes);
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Forks
{
	private boolean[] used = {false, false, false, false, false};
	public synchronized void takeFork()
	{
		Philosopher p = (Philosopher) Thread.currentThread();
		int id = p.id;
		while(used[id] || used[(id + 1) % 5])
		{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Philosopher " + id + " : takeFork!");
		used[id] = true;
		used[(id + 1) % 5] = true;
	}
	public synchronized void putFork()
	{
		Philosopher p = (Philosopher) Thread.currentThread();
		int id = p.id;
		System.out.println("Philosopher " + id + " : putFork!");
		used[id] = false;
		used[(id + 1) % 5] = false;
		notifyAll();
	}
}

public class DiningPhilosophers {
	public static void main(String[] args)
	{
		Forks f = new Forks();
		new Philosopher(f).start();
		new Philosopher(f).start();
		new Philosopher(f).start();
		new Philosopher(f).start();
		new Philosopher(f).start();
	}
}
