package circus.monitor;

public class ProducerConsumerMonitor {
	private static class ProdCons {
		int N = 80;
		int[] buffer = new int[N];
		int numItems = 0;
		int in = 0, out = 0;

		synchronized void produce(int item) {
			while (numItems == N) {
				try {
					wait();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			buffer[in] = item;
			in = (in + 1) % N;
			numItems++;
			notify();
		}

		synchronized int consume() {
			while (numItems == 0) {
				try {
					wait();
				} catch (Exception exc) {

				}
			}
			int retItem = buffer[out];
			out = (out + 1) % N;
			numItems--;
			notify();
			return out;
		}
	}

	public static void main(String args[]) {
		ProdCons pc = new ProdCons();
		Thread producer = new Thread() {
			public void run() {
				for (int i = 0; i < 100; i++) {
					pc.produce(i);
				}
			}
		};
		Thread consumer = new Thread() {
			public void run() {
				for (int i = 0; i < 100; i++) {
					int retItem = pc.consume();
				}
			}
		};

		producer.start();
		consumer.start();

		try {
			producer.join();
			consumer.join();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
