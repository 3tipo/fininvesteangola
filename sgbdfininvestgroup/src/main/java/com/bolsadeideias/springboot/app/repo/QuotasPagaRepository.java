package com.bolsadeideias.springboot.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bolsadeideias.springboot.app.modell.QuotaPaga;
import java.util.List;

public interface QuotasPagaRepository extends JpaRepository<QuotaPaga, Integer>{

	@Query(value = "SELECT id, anopago, datademulta, dataderecepcao, datapagamentomulta, \r\n" + 
			"       datapagamentoquota, descricao, estapaga, juros, mespago, multado, \r\n" + 
			"       numerorecibo, recebido, secretaria, tipodedocumento, valor, valordamulta, \r\n" + 
			"       fichado_socio\r\n" + 
			"  FROM quotaspagas q where fichado_socio = ? and recebido in(false) and multado in (false) and anopago = ?;", nativeQuery = true)
	List<QuotaPaga> finddivida(@Param(value = "fichado_socio") int fichado_socio,@Param(value = "anopago") String anopago);

	@Query(value = "SELECT id, anopago, bidosocio, codigodosocio, datademulta, dataderecepcao, \r\n" + 
			"       datalimite, datapagamentomulta, datapagamentoquota, descricao, \r\n" + 
			"       estapaga, juros, mespago, multado, nomedosocio, numerorecibo, \r\n" + 
			"       recebido, secretaria, tipodedocumento, valor, valordamulta, fichado_socio,nomedosocio,codigodosocio,bidosocio,mespagoint \r\n" + 
			"  FROM quotaspagas where anopago = ? and mespagoint = ? order by nomedosocio;", nativeQuery = true)
	List<QuotaPaga> listadePagamentodeQuotaPorAnoEmes(@Param( value = "anopago") String anopago, @Param(value = "mespagoint") String mespagoint);
	
	@Query(value = "SELECT id, anopago, bidosocio, codigodosocio, datademulta, dataderecepcao, \r\n" + 
			"       datalimite, datapagamentomulta, datapagamentoquota, descricao, \r\n" + 
			"       estapaga, juros, mespago, multado, nomedosocio, numerorecibo, \r\n" + 
			"       recebido, secretaria, tipodedocumento, valor, valordamulta, fichado_socio,nomedosocio,codigodosocio,bidosocio,mespagoint \r\n" + 
			"  FROM quotaspagas where anopago <= ? and mespagoint = ? and recebido in(false) and multado in (false) order by nomedosocio;\r\n" + 
			"",nativeQuery = true)
	List<QuotaPaga> listadeDividasdeQuotaporAnoEmes(@Param( value = "anopago") String anopago, @Param(value = "mespagoint") String mespagoint);
     
	
	@Query(value = "SELECT id, anopago, bidosocio, codigodosocio, datademulta, dataderecepcao, \r\n" + 
			"       datalimite, datapagamentomulta, datapagamentoquota, descricao, \r\n" + 
			"       estapaga, juros, mespago, multado, nomedosocio, numerorecibo, \r\n" + 
			"       recebido, secretaria, tipodedocumento, valor, valordamulta, fichado_socio,nomedosocio,codigodosocio,bidosocio,mespagoint \r\n" + 
			"  FROM quotaspagas  where fichado_socio = ? and anopago = ? and mespagoint = ? order by nomedosocio;", nativeQuery = true)
	List<QuotaPaga>   individual(@Param(value = "fichado_socio") int id ,@Param(value = "t_anopago") String t_anopago,@Param(value = "t_mespagoint") String t_mespagoint);
}
