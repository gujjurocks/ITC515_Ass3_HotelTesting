package hotel.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hotel.credit.CreditCard;
import hotel.utils.IOUtils;

public class Room {
	
	private enum State {READY, OCCUPIED}
	
	int id;
	RoomType roomType;
	List<Booking> bookings;
	State state;

	
	public Room(int id, RoomType roomType) {
		this.id = id;
		this.roomType = roomType;
		bookings = new ArrayList<>();
		state = State.READY;
	}
	

	public String toString() {
		return String.format("Room : %d, %s", id, roomType);
	}


	public int getId() {
		return id;
	}
	
	public String getDescription() {
		return roomType.getDescription();
	}
	
	
	public RoomType getType() {
		return roomType;
	}
	
	public boolean isAvailable(Date arrivalDate, int stayLength) {
		IOUtils.trace("Room: isAvailable");
		for (Booking b : bookings) {
			if (b.doTimesConflict(arrivalDate, stayLength)) {
				return false;
			}
		}
		return true;
	}
	
	
	public boolean isReady() {
		return state == State.READY;
	}


	public Booking book(Guest guest, Date arrivalDate, int stayLength, int numberOfOccupants, CreditCard creditCard) {
		// checks if room is available or not, if available then new booking is created and added to bookings list
		Booking booking = null;
		if(isAvailable(arrivalDate, stayLength) && booking.isPending())
		{
			Room room;
			booking = new Booking(guest, room, arrivalDate, stayLength, numberOfOccupants, creditCard)
			System.out.println("Booking done successfully.");
			bookings.add(booking);
			return booking;
		}
		else
		{
			System.out.println("Booking already exists.");
			return booking;
		}		
	}


	public void checkin() {
		// checks if room state is ready or not, if not then throws runtime exception and changes status to occupied
		if(!isReady())
		{
			throw new RuntimeException("Room is not READY");
		}
		state = State.OCCUPIED;
	}


	public void checkout(Booking booking) {
		// checks if room state is occupied or not, if not then throws runtime exception and changes status to ready and removes booking
		if(isReady())
		{
			throw new RuntimeException("Room is not OCCUPIED. Its ready.");
		}
		bookings.remove(booking);
		state = State.READY;
	}


}
