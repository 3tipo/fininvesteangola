package com.bolsadeideias.springboot.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bolsadeideias.springboot.app.modell.TaxasPga;
import java.lang.String;
import java.util.List;

public interface TaxasPagaRepository extends JpaRepository<TaxasPga, Integer>{

	@Query(value = "SELECT id, datademulta, dataderecepcao, multado, recebido, t_anopago, \r\n" + 
			"       t_datapagamento, t_descricao, t_estapaga, t_juros, t_mespago, \r\n" + 
			"       t_numerorecibo, t_secretaria, t_tipodedocumento, t_valor, valordamulta, \r\n" + 
			"       fichado_socio\r\n" + 
			"  FROM taxaspagas where fichado_socio = ? and recebido in(false) and multado in (false) and t_anopago = ?;", nativeQuery = true)
	List<TaxasPga> taxadivida(@Param(value = "fichado_socio") int fichado_socio,@Param(value = "anopago") String anopago);
	
	@Query(value = "SELECT id, bidosocio, codigodosocio, datademulta, dataderecepcao, datalimite, \r\n" + 
			"       multado, nomedosocio, recebido, t_anopago, t_datapagamento, t_descricao, \r\n" + 
			"       t_estapaga, t_juros, t_mespago, t_numerorecibo, t_secretaria, \r\n" + 
			"       t_tipodedocumento, t_valor, valordamulta, fichado_socio, t_mespagoint\r\n" + 
			"  FROM taxaspagas where t_anopago = ? and t_mespagoint = ? order by nomedosocio;",nativeQuery = true)
	List<TaxasPga> listadePagamentodeTaxaPorAnoEmes(@Param(value = "t_anopago") String t_anopago,@Param(value = "t_mespagoint") String t_mespagoint);

	@Query(value = "SELECT id, bidosocio, codigodosocio, datademulta, dataderecepcao, datalimite, \r\n" + 
			"       multado, nomedosocio, recebido, t_anopago, t_datapagamento, t_descricao, \r\n" + 
			"       t_estapaga, t_juros, t_mespago, t_numerorecibo, t_secretaria, \r\n" + 
			"       t_tipodedocumento, t_valor, valordamulta, fichado_socio, t_mespagoint\r\n" + 
			"  FROM taxaspagas where fichado_socio = ? and t_anopago = ? and t_mespagoint = ? order by nomedosocio;",nativeQuery = true)
	List<TaxasPga> individual(@Param(value = "fichado_socio") int id ,@Param(value = "t_anopago") String t_anopago,@Param(value = "t_mespagoint") String t_mespagoint);


@Query(value = "SELECT id, bidosocio, codigodosocio, datademulta, dataderecepcao, datalimite, \r\n" + 
		"       multado, nomedosocio, recebido, t_anopago, t_datapagamento, t_descricao, \r\n" + 
		"       t_estapaga, t_juros, t_mespago, t_numerorecibo, t_secretaria, \r\n" + 
		"       t_tipodedocumento, t_valor, valordamulta, fichado_socio, t_mespagoint \r\n" + 
		"  FROM taxaspagas where t_anopago = ? and t_mespagoint = ? and recebido in(false) and multado in (false) order by nomedosocio;",nativeQuery = true)
List<TaxasPga> listadeDividasdeTaxasporAnoeMes(@Param(value = "t_anopago") String t_anopago, @Param(value = "t_mespagoint") String t_mespagoint);

}
