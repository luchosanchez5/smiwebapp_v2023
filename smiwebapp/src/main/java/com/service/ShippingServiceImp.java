package com.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bea.xml.stream.samples.Parse;
import com.expandable.model.PackingSlip;
import com.expandable.model.Sofst;
import com.expandable.model.Contact;
import com.expandable.repository.ContactRep;
import com.expandable.repository.PackingslipRep;
import com.expandable.repository.SofstRep;
import com.model.Carrier;
import com.model.FreightCode;
import com.model.PackDimension;
import com.model.Quote;
import com.model.Shipment;
import com.model.TempItem;
import com.model.Users;
import com.repository.CarrierRepository;
import com.repository.FreightCodeRep;
import com.repository.PackdimensionRep;
import com.repository.ShipmentRep;
import com.repository.UserRep;
import com.request.ShipmentsRequest;
import com.response.PackingslipResponse;
import com.response.ShipmentDetailsResponse;
import com.response.ShipmentResponse;
import com.response.ShipmentsResponse;

@Service("shippingService")
public class ShippingServiceImp implements ShippingService {
	
	@Autowired
	private  PackingslipRep packigRep;
	
	@Autowired
	private CarrierRepository carrierRep;
	
	@Autowired
	private ShipmentRep shipmentRep;
	
	@Autowired
	private UserRep userRep;
	
	@Autowired
	private SofstRep sofstRep;
	
	@Autowired
	private EmailTemplateService templateServ;
	
	@Autowired
	private ContactRep contactRep;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PackdimensionRep packRep;
	
	@Autowired
	private FreightCodeRep freightRep;
	

	@Override
	public List<PackingSlip> listPendingShipments() {
		// TODO Auto-generated method stub
		
		List<PackingSlip> list =  packigRep.findAll();
		List<PackingSlip> listpack = list
				.stream()
				.filter(c -> c.getBillofLading().equals(""))
				.collect(Collectors.toList());
		
		
		
		return listpack;
	}

	@Override
	public PackingslipResponse getPackingById(String packingNo) {
		
		// TODO Auto-generated method stub
		PackingSlip pack  = packigRep.findOneByPackingSlipno(packingNo);
		PackingslipResponse packRes = new PackingslipResponse();
		
		packRes.setAddress1(pack.getAddress1());
		packRes.setAddress2(pack.getAddress2());
		packRes.setAddress3(pack.getAddress3());
		packRes.setCountry(pack.getCountry());
		packRes.setCity(pack.getCity().trim());
		packRes.setCustomerName(pack.getCustomerName());
		packRes.setCustPo(pack.getCustPo());
		packRes.setPackingSlipno(pack.getPackingSlipno());
		packRes.setShipTo(pack.getShipTo());
		packRes.setSoId(pack.getSoId());
		packRes.setSophoneNumber(pack.getSophoneNumber());
		packRes.setState(pack.getState().trim());
		packRes.setContactName(pack.getContactName());
		packRes.setPhoneNo(pack.getPhoneNo());
		packRes.setEmail(pack.getEmail());
		packRes.setZipCode(pack.getZipCode().trim());
		packRes.setCustomerId(pack.getCustomerId());
		packRes.setBatchNumber(Double.parseDouble(pack.getBatchNumber()));
	
		packRes.setCarrierId(carrierRep.findOneByExpCode(pack.getCarrier()).getCarrierId());
		
		//** New 06/23/2021   Not TEsted
		FreightCode freight  = freightRep.findOneByCode(pack.getFreightCode());
		packRes.setFreight(freight.getDescription());
		packRes.setBilltoAccount(pack.getBilltoAccount());
		
		if (freight.getCustomerCharges().equals("N")){
			
			packRes.setBilltoAccount("");
			
		}

		return packRes;
	}

	@Override
	public ShipmentDetailsResponse saveShipment(ShipmentResponse shipRe, String packingno, String user, String custshiAcc) {
		// TODO Auto-generated method stub
		Boolean result = true;	
		Boolean updatedTrack = true;
		Boolean sendAsn = true;
		String emailBody = "";
		ShipmentDetailsResponse shiDet = new ShipmentDetailsResponse();
		
		try {
			shiDet.setResult(false);
			Shipment ship = new Shipment();
			Carrier ca = new Carrier();
			Users usr = userRep.findByUsername(user);
			PackingSlip pack = packigRep.findOneByPackingSlipno(packingno);
			ship.setCreatedDate(LocalDateTime.now());
			ship.setApiLabelId(shipRe.getLabelResponse().getLabelId());
			ship.setApiShippingId(shipRe.getLabelResponse().getShipmentId());
			ship.setCarrier(carrierRep.findOneBycarrierId(shipRe.getLabelResponse().getCarrierId()));
			ship.setCustomerId(pack.getCustomerId());
			ship.setInsuranceCost(shipRe.getLabelResponse().getInsuranceCost().getAmount());
			ship.setIsInternational(shipRe.getLabelResponse().getIsInternational());
			ship.setLabelPdf(shipRe.getLabelResponse().getLabelDownload().getPdf());
			ship.setLabelZpl(shipRe.getLabelResponse().getLabelDownload().getZpl() );
			ship.setPackageCode(shipRe.getLabelResponse().getPackageCode());
			ship.setPackingSlipno(packingno);
			ship.setServiceDesc(shipRe.getLabelResponse().getServiceCode());
			ship.setShipmentCost(shipRe.getLabelResponse().getShipmentCost().getAmount());
			ship.setSoId(pack.getSoId());
			ship.setTrackingNumber(shipRe.getLabelResponse().getTrackingNumber());
			ship.setUser(usr);
			ship.setVoided(shipRe.getLabelResponse().getVoided());
			ship.setShipTo(pack.getShipTo());
			ship.setStatus("A");
			ship.setCustomerName(pack.getCustomerName());
			ship.setAddress(pack.getAddress1() + " " + pack.getAddress2() + " " + pack.getAddress3());
			ship.setBatchNumber(pack.getBatchNumber());		
			
			FreightCode freight  = freightRep.findOneByCode(pack.getFreightCode());
			ship.setFreightDesc(freight.getDescription());
			
			if (freight.getCustomerCharges().equals("Y")){
				Double factor = freight.getPercentCharges()/100;
				Double tot = shipRe.getLabelResponse().getShipmentCost().getAmount()/factor;
				ship.setExtraChargues(factor);
				ship.setCustomerAccount(custshiAcc);
				
			}
			
			shipmentRep.save(ship);
			
			shiDet.setResult(true);
			shiDet.setShip(ship);
			updatedTrack = updatepackingTracking(pack.getSoId(), Double.parseDouble(pack.getBatchNumber())  ,shipRe.getLabelResponse().getTrackingNumber());
			if (updatedTrack)
				shiDet.setUpdatedTracking(true);
			//sendAsn = sendAsnNotifications(ship, pack.getSoId(), Double.parseDouble(pack.getBatchNumber()),pack);
			


		}catch (Exception e) {
			result = false;
			System.out.println("ERROR SAVING : " +  e.getMessage());
		}




		return shiDet;
	}

	@Override
	public ShipmentsResponse findAllShipments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShipmentsResponse findAllShipmentsBySo(String soId) {
		// TODO Auto-generated method stub
		List<Shipment> listShip = new ArrayList<Shipment>();
		listShip = shipmentRep.findAll();
		
		List<Shipment> c = listShip
				.stream()
				.filter(cc -> cc.getSoId() == soId && cc.getStatus().equals("A") )
				.collect(Collectors.toList());
		
		return null;
	}

	@Override
	public ShipmentsResponse findAllShipmentsByCarrier(long carrierId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShipmentsResponse findAllShipmentsByCustomer(String customerId) {
		// TODO Auto-generated method stub
		
		List<Shipment> listShip = new ArrayList<Shipment>();
		listShip = shipmentRep.findAllByStatus("A");
		System.out.println("SIZE LIST SHIP : " +  String.valueOf(listShip.size()) );
		ShipmentsResponse shipResul = new ShipmentsResponse();
		
		List<Shipment> list = listShip
				.stream()
				.filter(c -> c.getCustomerId().startsWith(customerId))
				.collect(Collectors.toList());
		shipResul.setResult("Y");
		shipResul.setTotalRecords(list.size());
		shipResul.setShipmentList(list);
			
		
		return shipResul;
	}
	
	
	@Override
	public ShipmentsResponse findAllTodayShipments() {
		
		// TODO Auto-generated method stub
		List<Shipment> listShip = new ArrayList<Shipment>();
		listShip = shipmentRep.findAllByStatus("A");
		
		ShipmentsResponse shipResul = new ShipmentsResponse();
		
		List<Shipment> list = listShip
				.stream()
				.filter(c -> c.getCreatedDate().toLocalDate().isEqual(LocalDateTime.now().toLocalDate()))
				.collect(Collectors.toList());
		shipResul.setResult("Y");
		shipResul.setTotalRecords(list.size());
		shipResul.setShipmentList(list);
		System.out.println("SIZE LIST SHIP TODAY : " +  String.valueOf(list.size()));
		
		return shipResul;
	}

	@Override
	public Boolean updatepackingTracking(String soId, Double batchNumber, String tracking) {
		// TODO Auto-generated method stub
		Boolean result = true;
		try {
			List<Sofst> listPack = sofstRep.findAllBySoIdAndBatchNumber(soId, batchNumber);
			listPack.stream().forEach((c) -> { c.setBillofLanding(tracking);  sofstRep.save(c);});

		}catch (Exception e) {
			result = false;
			System.out.println("ERROR UPDATING TRACKING : " +  e.getMessage());
		}

		return result;
	}

	
	public boolean sendAsnNotifications(Shipment ship, String soId, Double batchNumber, PackingslipResponse packRes) {
		// TODO Auto-generated method stub
		Boolean result = true;
		try {
			Sofst temp;
			String emailBody = "";
			List<Sofst> listPack = sofstRep.findAllBySoIdAndBatchNumber(soId, batchNumber);
			System.out.println("LIST PACKINGS...");
			PackingSlip pack = packigRep.findOneByPackingSlipno(packRes.getPackingSlipno()) ;

			Iterator iTemItems = listPack.iterator();

			while (iTemItems.hasNext()) {
				Contact conTemp;
				temp = (Sofst) iTemItems.next();
				List<Contact> listContacts  = contactRep.findAllByCustomerId(pack.getCustomerId().trim());
				emailBody = templateServ.buildAsnTemplate(ship.getCustomerName(), ship.getCreatedDate(), 0.00, pack.getCustPo(), "",pack.getEmailTaker(), ship.getCarrier().getCarrierCode(), ship.getTrackingNumber());

				Iterator contacts = listContacts.iterator();

				while (contacts.hasNext()) {
					conTemp = (Contact) contacts.next();
					emailService.sendHTMlEmail("luisIt@sealmethodsinc.com", "Seal Methods Inc ASN | PO : " + pack.getCustPo() + " | P/N : " + temp.getPartId()+ "", emailBody);
					System.out.println("SENDING EMAIL : ");
				}

			}

			/*
			listPack.stream().forEach((c) -> { 
					List<Contact> listContacts  = contactRep.findAllByCustomerId(pack.getCustomerId());
					listContacts.stream().forEach((x) -> {
						try {
							emailService.sendHTMlEmail("luisIt@sealmethodsinc.com", "Seal Methods Inc ASN | PO : " + pack.getCustPo() + " | P/N : " + c.getPartId()+ "", body);
							System.out.println("SENDING EMAIL : ");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});					

				});*/

		} catch (Exception e) {
			result = false;
			System.out.println("ERROR SENDING ASN : " +  e.getMessage());
		}


		return false;
	}

	@Override
	public Shipment findOneById(long id) {
		// TODO Auto-generated method stub
		return shipmentRep.findOneById(id);
	}

	@Override
	public List<PackDimension> findAllPackDimensions() {
		// TODO Auto-generated method stub
		
		return packRep.findAll();
	}

	@Override
	public void cancelShipment(long id) {
		// TODO Auto-generated method stub
		boolean updatedTrack;
		Shipment ship = shipmentRep.findOneById(id);
		ship.setStatus("C");
		shipmentRep.save(ship);
		updatedTrack = updatepackingTracking(ship.getSoId(), Double.parseDouble(ship.getBatchNumber())  ,"");
	}




}
