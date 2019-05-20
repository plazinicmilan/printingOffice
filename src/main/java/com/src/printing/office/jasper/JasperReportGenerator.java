package com.src.printing.office.jasper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.src.printing.office.po.enums.TipDokumenta;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class JasperReportGenerator {

	@Value("${jasper.report.workOrder.path}")
	private String jasperReportWorkOrderPath;

	@Value("${jasper.report.deliveryNote.path}")
	private String jasperReportDeliveryNotePath;

	@Value("${jasper.report.receipt.path}")
	private String jasperReportReceiptPath;

	@Value("${jasper.report.material.path}")
	private String jasperReportMaterialPath;

	@Value("${jasper.report.material.consumption.path}")
	private String jasperReportMaterialConsumptionPath;

	@Value("${jasper.report.finished.product.path}")
	private String jasperReportFinishedProductPath;

	public File generatePDF(TipDokumenta tipDokumenta, String pdfName, Map<String, Object> parameters)
			throws Exception {
		InputStream report = null;
		FileOutputStream pdfOutputStream = null;
		try {

			File temp = new File(System.getProperty("java.io.tmpdir") + pdfName + ".pdf");

			report = new FileInputStream(getPathFromForTypeOfDocument(tipDokumenta));
			pdfOutputStream = new FileOutputStream(temp);

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(report);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			// brisanje praznih strana ukoliko se kreiraju
			for (Iterator<JRPrintPage> i = print.getPages().iterator(); i.hasNext();) {
				JRPrintPage page = i.next();
				if (page.getElements().size() == 0)
					i.remove();
			}

			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(print));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfOutputStream));
			exporter.exportReport();

			return temp;
		} catch (Exception e) {
			throw e;
		} finally {
			if (report != null)
				report.close();
			if (pdfOutputStream != null)
				pdfOutputStream.close();
		}
	}

	private String getPathFromForTypeOfDocument(TipDokumenta tipDokumenta) {

		switch (tipDokumenta) {
		case RADNI_NALOG:
			return jasperReportWorkOrderPath;
		case OTPREMNICA:
			return jasperReportDeliveryNotePath;
		case RACUN:
			return jasperReportReceiptPath;
		case MATERIJAL:
			return jasperReportMaterialPath;
		case MATERIJAL_POTROSNJA:
			return jasperReportMaterialConsumptionPath;
		case GOTOV_PROIZVOD:
			return jasperReportFinishedProductPath;
		default:
			return null;
		}
	}
}