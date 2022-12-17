package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.BusException;
import com.masai.exception.RouteException;
import com.masai.model.Bus;
import com.masai.model.Route;
import com.masai.repository.BusDao;
import com.masai.repository.RouteDao;

@Service
public class BusServiceImpl implements BusService {

	@Autowired
	BusDao busDao;
	
	@Autowired
	private RouteDao rDao;
	
	
//	Method to save Bus in database.
	@Override
	public Bus addBus(Bus bus , int routeId) throws BusException, RouteException {

		Optional<Route> route = rDao.findById(routeId);
		
			if(route.isPresent() ){
			
				 Route ro = route.get();
				 
				 ro.getBus().add(bus);
				 
				 bus.setRoutes(ro);
				
				return busDao.save(bus);
		}
			
			else
				throw new RouteException("No route available");
		
		
	}

// Method to update Bus
	@Override
	public Bus updateBus(Bus bus) throws BusException {
		
		Optional<Bus> opt = busDao.findById(bus.getBusId());
		
		if(opt.isPresent()) {
			Bus updatedBus=  busDao.save(bus);
			return updatedBus;
		}else {
			throw new BusException("Bus not found with Id: "+bus.getBusId());
		}
	}

//	Method to delete bus by busId
	@Override
	public Bus deleteBus(Integer busId) throws BusException {

		Optional<Bus> opt = busDao.findById(busId);
		
		if(opt.isPresent()) {
			Bus bus = opt.get();
			
			
			
			busDao.delete(bus);
			return bus;
		}else {
			throw new BusException("Canot delete bus,because bus is not found with id: "+busId);
		}
		
	}

//	Method to view bus by busId
	@Override
	public Bus viewBus(Integer busId) throws BusException {

		Optional<Bus> opt = busDao.findById(busId);
		
		if(opt.isPresent()) {
			Bus bus = opt.get();
			return bus;
		}else {
			throw new BusException("No bus present with Bus id:: "+busId);
		}
		
	}

//	Method for getting all by its type
	@Override
	public List<Bus> viewBusByType(String busType) throws BusException {

		List<Bus> buses = busDao.findByBusType(busType);
		
		if(buses.isEmpty()) {
			throw new BusException("No bus found with type "+busType);
		}else {
			return buses;
		}
		
	}

//	Method to view all bus.
	@Override
	public List<Bus> viewAllBus() throws BusException {

		List<Bus> buses = busDao.findAll();
		
		if(buses.isEmpty()) {
			throw new BusException("No bus found");
		}else {
			return buses;
		}
	}
	
	


	
	
}
