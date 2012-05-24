package hlmp.CommLayer;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Cola de objetos de eventos
 */
public class EventQueuePC {

	/**
	 * Cola de prioridad FIFO
	 */
	private ConcurrentLinkedQueue<Event> queue;

	/**
	 * Cantidad en la cola
	 */
	private int itemCount;

	/**
	 * Default Constructor
	 */
	public EventQueuePC()
	{
		queue = new ConcurrentLinkedQueue<Event>();
		itemCount = 0;
	}

	/**
	 * Desencola el primer objeto de la lista, se bloquea hasta que alguien inserte un elemento
	 * @return El primer objeto de la cola
	 */
	public synchronized Event draw()
	{
		while (itemCount == 0)
		{
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		Event eventHandler = queue.poll();
		this.itemCount--;
		return eventHandler;
	}

	/**
	 * Coloca un objeto en la cola
	 * @param m El objeto a colocar en la cola
	 */
	public synchronized void put(Event m)
	{
		this.queue.add(m);
		this.itemCount++;
		notify();
	}

	/**
	 * Desbloquea forzosamente el bloquedo de draw
	 */
	public synchronized void unblock(){
		notify();
	}
}
