package zclh.majasper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.lang.SerializationUtils;

import br.gov.serpro.edbv.dominio.domain.dto.ParametrosConsultaVoosJson;
import br.gov.serpro.edbv.dominio.domain.dto.report.voo.VooDTO;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException
    {

    	String camAtehPasta = args[0];
    	String pasta = args[1];
    	String nomeArquivo = args[2];
    		final java.nio.file.Path path = FileSystems.getDefault().getPath(camAtehPasta, pasta);
    		System.out.println(path);
    		try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
    			final WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
    			while (true) {
    				final WatchKey wk = watchService.take();
    				for (WatchEvent<?> event : wk.pollEvents()) {
    					// we only register "ENTRY_MODIFY" so the context is always
    					// a Path.
    					final java.nio.file.Path changed = (java.nio.file.Path) event.context();
    					System.out.println(changed + new Date().toString());
    					if (changed.toString().endsWith(nomeArquivo + ".jrxml")) {
    						System.out.println("My file has changed - voo");
    						JasperDesign desenho;
    						try {
    							String camSemExtensao = camAtehPasta + pasta + "/" + nomeArquivo;
    							String desSemExtensao = "/hoje/" + nomeArquivo;
    							desenho = JRXmlLoader.load(new FileInputStream(new File(camSemExtensao + ".jrxml")));
    							JasperCompileManager.compileReportToFile(desenho, desSemExtensao + ".jasper");
    							System.out.println("compilou");
    							ParametrosConsultaVoosJson parametros = (ParametrosConsultaVoosJson) SerializationUtils .deserialize(new FileInputStream("/hoje/prvd.bin"));

    							Map<String, Object> param; //UtilReport.montarParametros();
    							param = new HashMap<String, Object>();
//    							param.put(UtilReport.PARAM_RECINTO_TRABALHO, "recinto");
//    							param.put(UtilReport.PARAM_CPF_EMISSOR, "cpfEmissor");
//    							if (!EdbvUtil.isNullOrEmpty(parametros)) {
    								param.put("FILTROS_PESQUISA", parametros);
//    							}
    			List<VooDTO> listaVoos = new ArrayList<>();
    			listaVoos.add(new VooDTO(BigDecimal.TEN, "apvoNrVoo", "apvoCdCiaAerea", new Date()));

    							JRDataSource ds = new JRBeanCollectionDataSource((List) listaVoos);
    							JasperFillManager.fillReportToFile(desSemExtensao + ".jasper", desSemExtensao + ".jrprint", param, ds);
    							JasperExportManager.exportReportToPdfFile(desSemExtensao + ".jrprint");
    						} catch (JRException e) {
    							e.printStackTrace();
    						}

    					}
    				}
    				// reset the key
    				boolean valid = wk.reset();
    				if (!valid) {
    					System.out.println("Key has been unregisterede");
    				}
    			}
    }
    }
}
