package implement;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import interfaces.ClerkDetails;
import interfaces.RentalRequest;

public class RunnebleClerk implements Runnable{
	ClerkDetails fClerkDetails;
	List<RentalRequest> fRentalRequest;
	AtomicInteger fNumberOfRentalRequests;
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
