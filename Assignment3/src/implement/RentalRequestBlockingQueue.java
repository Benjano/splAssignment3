package implement;

import interfaces.RentalRequest;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class RentalRequestBlockingQueue implements BlockingQueue<RentalRequest> {

	@Override
	public RentalRequest remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RentalRequest poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RentalRequest element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RentalRequest peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<RentalRequest> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends RentalRequest> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean add(RentalRequest e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offer(RentalRequest e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void put(RentalRequest e) throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean offer(RentalRequest e, long timeout, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RentalRequest take() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RentalRequest poll(long timeout, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int remainingCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int drainTo(Collection<? super RentalRequest> c) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int drainTo(Collection<? super RentalRequest> c, int maxElements) {
		// TODO Auto-generated method stub
		return 0;
	}

}
