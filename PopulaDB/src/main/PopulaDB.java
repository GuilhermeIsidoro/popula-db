package main;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import database.DataBase;

@SuppressWarnings("unused")
public class PopulaDB {
	
	//Pai: Instituição.
	static String tb_instituicao 				= "INSERT INTO `estreaming`.`tb_instituicao` (`cnpj_instituicao`, `nme_instituicao`, `end_instituicao`, `cep_instituicao`, `tel_instituicao_1`, `tel_instituicao_2`, `eml_instituicao`, `url_instituicao`, `dta_insercao`, `sts_instituicao`, `razao_social`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	static String tb_faculdade	  				= "INSERT INTO `estreaming`.`tb_faculdade` (`idt_faculdade`, `nme_faculdade`, `cod_instituicao`, `sts_faculdade`, `dta_insercao`, `cod_usuario_admin`) VALUES (?, ?, ?, ?, ?, ?)";
	 
	//Pai: Curso. Filho: Instituição.
	static String tb_curso 					= "INSERT INTO `estreaming`.`tb_curso` (`idt_curso`, `nme_curso`, `sts_curso`, `cod_faculdade`, `dta_insercao`, `qtd_semestres`, `cod_usuario_coordenador`) VALUES (?, ?, ?, ?, ?, ?, ?)";
     
	//Pai: Usuário. Filho: Faculdade, Curso.
	static String tb_usuario 					= "INSERT INTO `estreaming`.`tb_usuario` (`idt_usuario`, `cpf_usuario`, `nme_usuario`, `eml_usuario`, `pwd_usuario`, `tpo_usuario`, `dta_insercao`, `sts_usuario`, `lgn_especial`, `tel_usuario`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	static String ta_usuario_faculdade 		= "INSERT INTO `estreaming`.`ta_usuario_faculdade` (`cod_usuario`, `cod_faculdade`, `sts_usuario_faculdade`) VALUES (?, ?, ?)";
	static String ta_usuario_cursos 			= "INSERT INTO `estreaming`.`ta_usuario_cursos` (`cod_curso`, `cod_usuario`, `sts_usuario_cursos`, `dta_insercao`) VALUES (?, ?, ?, ?)";
	 
	//Pai: Disciplina. Filho: Curso. 
	static String tb_disciplina 				= "INSERT INTO `estreaming`.`tb_disciplina` (`idt_disciplina`, `nme_disciplina`, `sts_disciplina`, `dta_insercao`, `sem_disciplina`) VALUES (?, ?, ?, ?, ?);"; 
	static String ta_disciplina_curso 			= "INSERT INTO `estreaming`.`ta_disciplina_cursos` (`cod_disciplina`, `cod_curso`, `sts_disciplina_cursos`) VALUES (?, ?, ?)";
  
	 
	//Pai: Assunto. Filho: Usuário.
	static String tb_assunto 					= "INSERT INTO `estreaming`.`tb_assunto` (`idt_assunto`, `txt_tema_assunto`, `cod_disciplina`, `dta_insercao`, `sts_assunto`, `cod_usuario_criador`, `qtd_atividades`) VALUES (?, ?, ?, ?, ?, ?, ?)";
	static String tb_notas 					= "INSERT INTO `estreaming`.`tb_notas` (`cod_usuario`, `cod_assunto`, `nota`) VALUES (?, ?, ?)";
	 
	//Pai: Midia. Filho: Assunto.
	static String tb_tipo_midia 				= "INSERT INTO `estreaming`.`tb_tipo_midia` (`idt_tipo_midia`, `tpo_midia`, `sts_tipo_midia`) VALUES (?, ?, ?)";
	static String tb_midia 					= "INSERT INTO `estreaming`.`tb_midia` (`idt_midia`, `txt_caminho`, `cod_tipo_midia`, `sts_midia`, `dta_insercao`) VALUES (?, ?, ?, ?, ?)";
	static String ta_assunto_midia 			= "INSERT INTO `estreaming`.`ta_assunto_midia` (`cod_assunto`, `cod_midia`, `sts_assunto_midia`) VALUES (?, ?, ?)";
	 
	//Pai: TipoQuestão.
	static String tb_tipo_questao 				= "INSERT INTO `estreaming`.`tb_tipo_questao` (`idt_tipo_questao`, `nme_tipo_questao`) VALUES (?, ?)";
	 
	//Pai: Assunto, Completar. Filho: Completar Alternativas.
	static String tb_completar 				= "INSERT INTO `estreaming`.`tb_completar` (`txt_enunciado`, `cod_assunto`, `sts_completar`, `idt_completar`, `ord_completar`, `qtd_campos`, `txt_frase`, `dta_insercao`, `cod_tipo_questao`, `flg_finalizada`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	static String tb_completar_alternativas 	= "INSERT INTO `estreaming`.`tb_completar_alternativas` (`idt_completar_alternativas`, `ord_completar_alternativas`, `txt_palavra`, `cod_completar`, `sts_completar_alternativas`) VALUES (?, ?, ?, ?, ?)";
 
	//Pai: Assunto, Mult Escolha. Filho: Mult Escolha Alternativas	
	static String tb_mult_escolha 				= "INSERT INTO `estreaming`.`tb_mult_escolha` (`txt_enunciado`, `cod_assunto`, `sts_mult_escolha`, `idt_mult_escolha`, `ord_mult_escolha`, `dta_insercao`, `vlr_questao`, `cod_tipo_questao`, `flg_finalizada`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	static String tb_mult_alternativas 		= "INSERT INTO `estreaming`.`tb_mult_alternativas` (`idt_alternativas`, `txt_item`, `correto`, `cod_mult_escolha`, `sts_mult_alternativas`) VALUES (?, ?, ?, ?, ?)";

	//Pai: Assunto, VF. Filho: VF Alternativas.
	static String tb_v_f 						= "INSERT INTO `estreaming`.`tb_v_f` (`cod_assunto`, `sts_v_f`, `idt_vf`, `txt_enunciado`, `ord_v_f`, `dta_insercao`, `cod_tipo_questao`, `flg_finalizada`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	static String tb_v_f_alternativas 			= "INSERT INTO `estreaming`.`tb_v_f_alternativas` (`idt_v_f_alternativas`, `txt_item`, `vlr_v_f`, `cod_v_f`, `sts_v_f_alternativas`) VALUES (?, ?, ?, ?, ?)";
	 
	//Pai: Assunto, Somatório. Filho: Somatório Alternativas
	static String tb_somatorio 				= "INSERT INTO `estreaming`.`tb_somatorio` (`idt_somatorio`, `txt_enunciado`, `cod_assunto`, `sts_somatorio`, `soma`, `ord_somatorio`, `dta_insercao`, `cod_tipo_questao`, `flg_finalizada`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	static String tb_somatorio_alternativas 	= "INSERT INTO `estreaming`.`tb_somatorio_alternativas` (`idt_somatorio_alternativas`, `txt_item`, `vlr_somatorio_alternativas`, `cod_somatorio`, `sts_somatorio_alternativas`) VALUES (?, ?, ?, ?, ?)";
	
	static int qtdInserts = 1000;
	
	static HashMap<String, String> tipos = new HashMap<String,String>();
	static HashMap<String, String> tabelas = new HashMap<String,String>();
	
	public static void main (String[] args) {
	
		DataBase database = new DataBase();
		
		try {
			
			Connection conn = database.getConnection();			
			
			popula(conn);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
private static void popula(Connection conn) throws Exception {
	
	
	    Iterator it = tabelas.entrySet().iterator();
	    
	    while (it.hasNext()) {
	    	
	        Map.Entry pair = (Map.Entry)it.next();
	        
	        String tabela = pair.getKey().toString();
	        String template = pair.getValue().toString();
	        
	        PreparedStatement ps = null;
			ResultSet rs;

			try {
				
				DatabaseMetaData databaseMetaData = conn.getMetaData();
				
				rs = databaseMetaData.getColumns(null, null, tabela, null);	
				
				String nomeColuna;
				String tipoColuna = null;
				int tamanhoColuna = 0;
				int indiceColuna;
				
				int indexColuna = 0;
				
				while (rs.next()) {
					
					indexColuna ++;
					
					nomeColuna = rs.getString("COLUMN_NAME");
				    tipoColuna = rs.getString("TYPE_NAME");
				    tamanhoColuna = rs.getInt("COLUMN_SIZE");
				    indiceColuna = rs.getInt ("ORDINAL_POSITION");
				}
				
				ps = conn.prepareStatement(template);
					
				for (int i = 0; i < qtdInserts; i++) {
				
					if (tipoColuna.equals("INT")) {
						
						int valor = getIntAleatorio(tamanhoColuna);	
						
						ps.setInt(i, valor);
						
					} else if (tipoColuna.equals("BIT")) {
						
						ps.setShort(i, (short) 1);
						
					} else if (tipoColuna.equals("DATETIME")) {
						
						ps.setDate(i, getData());
					} else if (tipoColuna.equals("FLOAT")) {
						
						ps.setFloat(i, getFloatAleatoria(tamanhoColuna));
					} else {
						
						ps.setString(i, getStringAleatoria(tamanhoColuna));
					}					
				}
				
				rs = ps.executeQuery();
								
			} catch (SQLException e) {
				
				e.printStackTrace();
			}	
	        
	        it.remove();
	    }	
	}
	
	private static String getStringAleatoria(int tamanho) {
		
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		
		int letraASC = 0; 	
		
		for (int i = 0; i < tamanho; i++) {
			
			letraASC = random.nextInt(122 + 1 - 97) + 97;
			
			sb.append(Character.toString((char) letraASC));			
		}
		
		return sb.toString();
	}
	
	private static Integer getIntAleatorio(int tamanho) {
		
		Random random = new Random();
			
		int retorno = random.nextInt(tamanho) + 1;
			
		return retorno;
	}
	
	private static float getFloatAleatoria(int tamanho) {
		
		Random random = new Random();
			
		float retorno = (float) (random.nextInt(tamanho) + 1);
			
		return retorno;
	}
	
	private static Date getData() {
		
		return new Date(Calendar.getInstance().getTimeInMillis());
	}
	
	/*private static void insertInstituicao(Connection con) throws Exception {
		
		
		PreparedStatement ps = null;
		ResultSet rs;
		
		DatabaseMetaData databaseMetaData = con.getMetaData();

		rs = databaseMetaData.getColumns(null, null, "tb_instituicao", null);
		
		while (rs.next()) {
			
			String name = rs.getString("COLUMN_NAME");
		    String type = rs.getString("TYPE_NAME");
		    int size = rs.getInt("COLUMN_SIZE");
		}
		
		try {
		
			ps = con.prepareStatement("select idt_instituicao from tb_instituicao");
			rs = ps.executeQuery();
			
			if (rs.isBeforeFirst()) {
				
				throw new Exception("Tabela tb_intituicao já está populada");
			}
			
			for (int i = 0; i < qtdInserts; i++) {
				
				String cnpj_instituicao 	=	getStringAleatoria(18);	
				String nme_instituicao 		= 	getStringAleatoria(80);	
				String end_instituicao 		= 	getStringAleatoria(100);	
				String cep_instituicao 		= 	getStringAleatoria(10);	
				String tel_instituicao_1	= 	getStringAleatoria(11);	
				String tel_instituicao_2 	= 	getStringAleatoria(11);	
				String eml_instituicao 		= 	getStringAleatoria(80);	
				String url_instituicao 		= 	getStringAleatoria(80);	
				Date dta_insercao 			= 	new java.sql.Date(Calendar.getInstance().getTimeInMillis());	
				int sts_instituicao 		= 	1;	
				String razao_social 		= 	getStringAleatoria(100);			
				
				ps = con.prepareStatement(tb_instituicao);
				
				ps.setString(1, cnpj_instituicao);
				ps.setString(2, nme_instituicao);
				ps.setString(3, end_instituicao);
				ps.setString(4, cep_instituicao);
				ps.setString(5, tel_instituicao_1);
				ps.setString(6, tel_instituicao_2);
				ps.setString(7, eml_instituicao);
				ps.setString(8, url_instituicao);
				ps.setDate(9, dta_insercao);
				ps.setInt(10, sts_instituicao);
				ps.setString(11, razao_social);
				
				ps.executeUpdate();
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}*/
	
	
	public void init() {
		
		tabelas.put("tb_v_f_alternativas"      ,"INSERT INTO `estreaming`.`tb_v_f_alternativas` (`txt_item`, `vlr_v_f`, `cod_v_f`, `sts_v_f_alternativas`) VALUES (?, ?, ?, ?)");
		tabelas.put("tb_v_f"                   ,"INSERT INTO `estreaming`.`tb_v_f` (`cod_assunto`, `sts_v_f`, `txt_enunciado`, `ord_v_f`, `dta_insercao`, `cod_tipo_questao`, `flg_finalizada`) VALUES (?, ?, ?, ?, ?, ?, ?)");
		tabelas.put("tb_usuario"               ,"INSERT INTO `estreaming`.`tb_usuario` (`cpf_usuario`, `nme_usuario`, `eml_usuario`, `pwd_usuario`, `tpo_usuario`, `dta_insercao`, `sts_usuario`, `lgn_especial`, `tel_usuario`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		tabelas.put("tb_tipo_questao"          ,"INSERT INTO `estreaming`.`tb_tipo_questao` (`nme_tipo_questao`) VALUES (?)");
		tabelas.put("tb_tipo_midia"            ,"INSERT INTO `estreaming`.`tb_tipo_midia` (`tpo_midia`, `sts_tipo_midia`) VALUES (?, ?)");
		tabelas.put("tb_somatorio_alternativas","INSERT INTO `estreaming`.`tb_somatorio_alternativas` (`txt_item`, `vlr_somatorio_alternativas`, `cod_somatorio`, `sts_somatorio_alternativas`) VALUES (?, ?, ?, ?)");
		tabelas.put("tb_somatorio"             ,"INSERT INTO `estreaming`.`tb_somatorio` (`txt_enunciado`, `cod_assunto`, `sts_somatorio`, `soma`, `ord_somatorio`, `dta_insercao`, `cod_tipo_questao`, `flg_finalizada`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		tabelas.put("tb_notas"                 ,"INSERT INTO `estreaming`.`tb_notas` (`cod_usuario`, `cod_assunto`, `nota`) VALUES (?, ?, ?)");
		tabelas.put("tb_mult_escolha"          ,"INSERT INTO `estreaming`.`tb_mult_escolha` (`txt_enunciado`, `cod_assunto`, `sts_mult_escolha`, `ord_mult_escolha`, `dta_insercao`, `vlr_questao`, `cod_tipo_questao`, `flg_finalizada`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		tabelas.put("tb_mult_alternativas"     ,"INSERT INTO `estreaming`.`tb_mult_alternativas` (`txt_item`, `correto`, `cod_mult_escolha`, `sts_mult_alternativas`) VALUES (?, ?, ?, ?)");
		tabelas.put("tb_midia"                 ,"INSERT INTO `estreaming`.`tb_midia` (`txt_caminho`, `cod_tipo_midia`, `sts_midia`, `dta_insercao`) VALUES (?, ?, ?, ?)");
		tabelas.put("tb_instituicao"           ,"INSERT INTO `estreaming`.`tb_instituicao` (`cnpj_instituicao`, `nme_instituicao`, `end_instituicao`, `cep_instituicao`, `tel_instituicao_1`, `tel_instituicao_2`, `eml_instituicao`, `url_instituicao`, `dta_insercao`, `sts_instituicao`, `razao_social`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		tabelas.put("tb_faculdade"             ,"INSERT INTO `estreaming`.`tb_faculdade` (`nme_faculdade`, `cod_instituicao`, `sts_faculdade`, `dta_insercao`, `cod_usuario_admin`) VALUES (?, ?, ?, ?, ?)");
		tabelas.put("tb_disciplina"            ,"INSERT INTO `estreaming`.`tb_disciplina` (`nme_disciplina`, `sts_disciplina`, `dta_insercao`, `sem_disciplina`) VALUES (?, ?, ?, ?)");
		tabelas.put("tb_curso"                 ,"INSERT INTO `estreaming`.`tb_curso` (`nme_curso`, `sts_curso`, `cod_faculdade`, `dta_insercao`, `qtd_semestres`, `cod_usuario_coordenador`) VALUES (?, ?, ?, ?, ?, ?)");
		tabelas.put("tb_completar_alternativas","INSERT INTO `estreaming`.`tb_completar_alternativas` (`ord_completar_alternativas`, `txt_palavra`, `cod_completar`, `sts_completar_alternativas`) VALUES (?, ?, ?, ?)");
		tabelas.put("tb_completar"             ,"INSERT INTO `estreaming`.`tb_completar` (`txt_enunciado`, `cod_assunto`, `sts_completar`, `ord_completar`, `qtd_campos`, `txt_frase`, `dta_insercao`, `cod_tipo_questao`, `flg_finalizada`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		tabelas.put("tb_assunto"               ,"INSERT INTO `estreaming`.`tb_assunto` (`txt_tema_assunto`, `cod_disciplina`, `dta_insercao`, `sts_assunto`, `cod_usuario_criador`, `qtd_atividades`) VALUES (?, ?, ?, ?, ?, ?)");
		tabelas.put("ta_usuario_faculdade"     ,"INSERT INTO `estreaming`.`ta_usuario_faculdade` (`cod_usuario`, `cod_faculdade`, `sts_usuario_faculdade`) VALUES (?, ?, ?)");
		tabelas.put("ta_usuario_cursos"        ,"INSERT INTO `estreaming`.`ta_usuario_cursos` (`cod_curso`, `cod_usuario`, `sts_usuario_cursos`, `dta_insercao`) VALUES (?, ?, ?, ?)");
		tabelas.put("ta_disciplina_cursos"     ,"INSERT INTO `estreaming`.`ta_disciplina_cursos` (`cod_disciplina`, `cod_curso`, `sts_disciplina_cursos`) VALUES (?, ?, ?)");
		tabelas.put("ta_assunto_midia"         ,"INSERT INTO `estreaming`.`ta_assunto_midia` (`cod_assunto`, `cod_midia`, `sts_assunto_midia`) VALUES (?, ?, ?)");
		
		tipos.put("INT", "getIntAleatorio");
		tipos.put("VARCHAR", "getStringAleatorio");
		tipos.put("BIT", "1");
		tipos.put("DATETIME", "getData");
		tipos.put("TEXT", "getStringAleatoria");
		tipos.put("CHAR", "getStringAleatoria");
		tipos.put("FLOAT", "getFloatAleatoria");
	}
	
	
	
}
