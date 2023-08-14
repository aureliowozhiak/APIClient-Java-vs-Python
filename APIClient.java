import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.BufferedWriter;
import java.io.FileReader;

public class APIClient {
    private static final String BASE_URL = "https://dataengineer.help/api/b9dd9efdee38083cce85ad665d786880/day/api.php";

    public static void main(String[] args) {
        int idToCheck = 1;
        String fileName = "data_from_java.json";
        try {

            while (true) {
                String endpoint = BASE_URL + "?id=" + idToCheck;
                String responseData = getData(endpoint);

                if (responseData.isEmpty()) {
                    break;
                }

                System.out.println(responseData);

                // Salvar os dados em um arquivo
                saveDataToFile(responseData, fileName);

                idToCheck++;
            }
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }

    
            handleFile(fileName, "data_from_java_processed.json");


    }

    private static String getData(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            return response.toString();
        } else {
            throw new IOException("Request failed with status code: " + responseCode);
        }
    }

    private static void saveDataToFile(String data, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(data);
            writer.write(System.lineSeparator());
        }
    }

    private static void handleFile(String fileNameSource, String fileNameTarget) {



        try {
                    BufferedReader reader = new BufferedReader(new FileReader(fileNameSource));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileNameTarget));

                    writer.write("[\n"); // Adiciona [ no começo do arquivo

                    String linha;
                    boolean primeiraLinha = true;
                    while ((linha = reader.readLine()) != null) {
                        if (!primeiraLinha) {
                            writer.write(",\n"); // Adiciona vírgula no final da linha, exceto na primeira linha
                        }
                        writer.write(linha);
                        primeiraLinha = false;
                    }

                    writer.write("\n]"); // Adiciona ] no fim do arquivo

                    reader.close();
                    writer.close();

                    System.out.println("Arquivo processado com sucesso.");

                } catch (IOException e) {
                    e.printStackTrace();
                }



    }
}
