package com.bolsadeideias.springboot.app.relatorio;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.bolsadeideias.springboot.app.modell.QuotaPaga;
import com.bolsadeideias.springboot.app.modell.TaxasPga;
import com.bolsadeideias.springboot.app.repo.QuotasPagaRepository;
import com.bolsadeideias.springboot.app.repo.TaxasPagaRepository;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class Relactorio {

	@Autowired
	private TaxasPagaRepository taxasPagaRepository;
	@Autowired
	private QuotasPagaRepository quotasPagaRepository;
	
     public void reciboDeTaxa(HttpServletResponse response, int id,String secretaria) throws JRException, IOException {
		
		TaxasPga taxa = taxasPagaRepository.findById(id).get();

		response.setContentType("text/html");
		//JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ris);
		Map<String, Object> parameters = new HashMap<>();
		
		//usuario_e_cargo é a variável que recebe os dados de quem assina os termos de impressão.
		parameters.put("nomedosocio",taxa.getNomedosocio());
		parameters.put("numerorecibo",taxa.getT_numerorecibo() );
		parameters.put("codigo",taxa.getCodigodosocio());
		parameters.put("secretaria",secretaria );
		parameters.put("valor",taxa.getT_valor());
		parameters.put("datarecepcao",taxa.getDataderecepcao() );
		parameters.put("mesano",taxa.getT_mespago()+" DE "+ taxa.getT_anopago() );
		
		File file = ResourceUtils.getFile("classpath:templates/relatorio/taxa.jrxml");
		JasperDesign design = JRXmlLoader.load(file);
		JasperReport jasperReport = JasperCompileManager.compileReport(design);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource() );
		response.setContentType("application/pdf");
		response.addHeader("Content-disposition", "filename="+"reciboDeTaxa"+taxa.getT_mespago()+""+ taxa.getT_anopago()+taxa.getNomedosocio()+".pdf;");
		java.io.OutputStream responseOutputStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, responseOutputStream);
	
	}
	
     public void reciboDeQuota(HttpServletResponse response, int id,String secretaria) throws JRException, IOException {
 		
 		QuotaPaga quota = quotasPagaRepository.findById(id).get();

 		response.setContentType("text/html");
 		//JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ris);
 		Map<String, Object> parameters = new HashMap<>();
 		
 		//usuario_e_cargo é a variável que recebe os dados de quem assina os termos de impressão.
 		parameters.put("nomedosocio",quota.getNomedosocio());
 		parameters.put("numerorecibo",quota.getNumerorecibo() );
 		parameters.put("codigo",quota.getCodigodosocio());
 		parameters.put("secretaria",secretaria );
 		parameters.put("valor",quota.getValor());
 		parameters.put("datarecepcao",quota.getDataderecepcao() );
 		parameters.put("mesano",quota.getMespago()+" DE "+ quota.getAnopago() );
 		
 		File file = ResourceUtils.getFile("classpath:templates/relatorio/quota.jrxml");
 		JasperDesign design = JRXmlLoader.load(file);
 		JasperReport jasperReport = JasperCompileManager.compileReport(design);
 		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,new JREmptyDataSource() );
 		response.setContentType("application/pdf");
 		response.addHeader("Content-disposition", "filename="+"reciboDeQuota"+quota.getMespago()+""+ quota.getAnopago()+quota.getNomedosocio()+".pdf;");
 		java.io.OutputStream responseOutputStream = response.getOutputStream();
 		JasperExportManager.exportReportToPdfStream(jasperPrint, responseOutputStream);
 	
 	}
	
}
