package vinelouzada;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        LocalDate dataInicio = LocalDate.parse("2024-01-01");
        Pattern pattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})");
        String url = "https://cursos.alura.com.br/forum/usuario/vinelouzada/acompanhando/todos/";

            int pagina = 1;
            int contadorDeTopicosRespondidos = 0;
            boolean continuaBusca = true;

            while (continuaBusca) {

                var doc = Jsoup.connect(url + pagina).get();
                Elements topicosDoForum = doc.select("time");

                for (Element topico: topicosDoForum) {

                    Matcher matcher = pattern.matcher(topico.toString());

                    if (matcher.find()) {
                        String dataFormatada = matcher.group(1);

                        LocalDate dataForum = LocalDate.parse(dataFormatada);

                        if (dataInicio.compareTo(dataForum) < 0) {
                            contadorDeTopicosRespondidos++;
                        }else{
                            continuaBusca = false;
                            break;
                        }
                    }
                }

                pagina++;
            }
        System.out.println("Periodo de busca: " + dataInicio + " e " + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println("Tópicos respondidos no fórum sem contar tópicos privados: " + contadorDeTopicosRespondidos);
    }
}