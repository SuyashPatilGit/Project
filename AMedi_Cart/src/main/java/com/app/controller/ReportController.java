package com.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.dto.DeliveryBoyDto;
import com.app.dto.SupplierDto;
import com.app.entities.Address;
import com.app.entities.OrderDetails;
import com.app.entities.Orders;
import com.app.entities.User;
import com.app.repositary.IOrderItemRepositary;
import com.app.service.DeliveryBoy.IDeliveryBoyService;
import com.app.service.Order.IOrderService;
import com.app.service.Supplier.ISupplierService;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Slf4j
@Controller
//@RequestMapping("/report")
@PermitAll
public class ReportController {

	@Autowired
	private IDeliveryBoyService dbService;

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IOrderItemRepositary orderItems;

	@Autowired

	private ISupplierService suppService;

	@GetMapping("/report/suplist")
	public ResponseEntity<byte[]> getSupplierList() throws JRException, IOException {

		log.info("In PDF service :exportReport ");

		List<SupplierDto> allSupplier = suppService.getAllSupplier();
		// load file and compile it

		File file = ResourceUtils.getFile("classpath:customer.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

		// gettig data source
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(allSupplier);
		Map<String, Object> parameters = new HashedMap<>();
		parameters.put("name", "SUPPLIER LIST");

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

		// JasperExportManager.exportReportToPdfFile(jasperPrint, path +
		// "\\customer.pdfS");
		byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=customerlist.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);

	}

	@GetMapping("/report/dblist")
	public ResponseEntity<byte[]> getDeliveryBoyList() throws JRException, IOException {

		log.info("In PDF service :exportReport ");

		List<DeliveryBoyDto> allDeliveryBoy = dbService.getAllDeliveryBoy();
		// load file and compile it

		File file = ResourceUtils.getFile("classpath:customer.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

		// gettig data source
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(allDeliveryBoy);
		Map<String, Object> parameters = new HashedMap<>();
		parameters.put("name", "DELIVERY BOY LIST");

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

		// JasperExportManager.exportReportToPdfFile(jasperPrint, path +
		// "\\customer.pdfS");
		byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=customerlist.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);

	}

	// pdf generator for invoice

	@GetMapping("/report/invoice/{id}")
	public ResponseEntity<byte[]> downloadInvoice(@PathVariable("id") int id) throws JRException, IOException {

		log.info("In PDF service :exportReport ");

		Orders orders = orderService.getOrders(id);

		List<OrderDetails> ord = orderItems.getByOrder(orders);
		// getting things to set as a parameter in jrxml
		Double totalAmount = orders.getTotalAmount();
		User user = orders.getUser();
		String fullName = user.getFirstName() + " " + user.getLastName();
		// get address line
		Address adr = orders.getDeliveryAddress();
		String address = adr.getAddressLine();
		String state = adr.getState();

		File file = ResourceUtils.getFile("classpath:invoice.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		System.out.println(fullName + " " + address + " " + state + " " + totalAmount);
		// gettig data source
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ord);
		Map<String, Object> parameters = new HashedMap<>();
		parameters.put("address", address);
		parameters.put("totalAmount", totalAmount);
		parameters.put("State", state);
		parameters.put("fullName", fullName);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

		// JasperExportManager.exportReportToPdfFile(jasperPrint, path +
		// "\\customer.pdfS");
		byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=invoice.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);

	}
}
