package wavefancy.TwoGroupMutations;

/**
 * Record the numer of tasks in queue.
 * @author icorner
 *
 */
public class QueueSize {
	int QUEURE_SIZE = 0;
	
	public synchronized void increaseOne() {
		this.QUEURE_SIZE++;
	}
	
	public synchronized void decreaseOne() {
		this.QUEURE_SIZE--;
	}
	
	public synchronized int getQueueSize() {
		return this.QUEURE_SIZE;
	}
}
