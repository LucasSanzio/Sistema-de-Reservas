import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class SistemaReservasHotel {
    private Map<String, Map<String, String>> reservas;
    private Map<String, List<Map<String, String>>> listaEspera;

    public SistemaReservasHotel() {
        reservas = new HashMap<>();
        listaEspera = new HashMap<>();
    }

    public void adicionarReserva(String codigoReserva, String dataCheckin, Map<String, String> detalhesReserva) {
        String nomeHotel = detalhesReserva.get("nome_hotel");
        String numeroQuarto = detalhesReserva.get("numero_quarto");

        if (!dataDisponivel(nomeHotel, numeroQuarto, dataCheckin)) {
            detalhesReserva.put("codigo_reserva", codigoReserva);
            String chaveFilaEspera = nomeHotel + "-" + numeroQuarto + "-" + dataCheckin;
            listaEspera.putIfAbsent(chaveFilaEspera, new LinkedList<>());
            listaEspera.get(chaveFilaEspera).add(detalhesReserva);
            System.out.println("Quarto já reservado para essa data. Adicionado à lista de espera. Código da reserva: " + codigoReserva);
            return;
        }

        detalhesReserva.put("codigo_reserva", codigoReserva);
        reservas.put(codigoReserva, detalhesReserva);
        System.out.println("Reserva adicionada com sucesso para o quarto " + numeroQuarto + " do hotel " + nomeHotel + " na data " + dataCheckin + ". Código da reserva: " + codigoReserva);
    }

    public Map<String, String> obterReserva(String codigoReserva) {
        return reservas.get(codigoReserva);
    }

    public void removerReserva(String codigoReserva) {
        if (reservas.containsKey(codigoReserva)) {
            Map<String, String> reservaRemovida = reservas.remove(codigoReserva);
            System.out.println("Reserva removida com código: " + codigoReserva);

            String nomeHotel = reservaRemovida.get("nome_hotel");
            String numeroQuarto = reservaRemovida.get("numero_quarto");
            String dataCheckin = reservaRemovida.get("data_checkin");
            String chaveFilaEspera = nomeHotel + "-" + numeroQuarto + "-" + dataCheckin;

            if (listaEspera.containsKey(chaveFilaEspera) && !listaEspera.get(chaveFilaEspera).isEmpty()) {
                Map<String, String> reservaPromovida = listaEspera.get(chaveFilaEspera).remove(0);
                reservas.put(reservaPromovida.get("codigo_reserva"), reservaPromovida);
                System.out.println("Reserva da lista de espera promovida para ativa com código: " + reservaPromovida.get("codigo_reserva"));

                if (listaEspera.get(chaveFilaEspera).isEmpty()) {
                    listaEspera.remove(chaveFilaEspera);
                }
            }
        } else {
            System.out.println("Reserva não encontrada.");
        }
    }

    private boolean dataDisponivel(String nomeHotel, String numeroQuarto, String dataCheckin) {
        for (Map<String, String> reserva : reservas.values()) {
            String hotelReservado = reserva.get("nome_hotel");
            String quartoReservado = reserva.get("numero_quarto");
            String checkoutReservaExistente = reserva.get("data_checkout");

            if (hotelReservado.equals(nomeHotel) && quartoReservado.equals(numeroQuarto)) {
                if (checkoutReservaExistente.compareTo(dataCheckin) <= 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public void lerReservasDeArquivo(String nomeArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(":");
                if (partes.length == 2) {
                    String codigoReserva = partes[0];
                    String[] detalhes = partes[1].split(",");
                    if (detalhes.length == 5) {
                        String nomeHospede = detalhes[0].trim();
                        String nomeHotel = detalhes[1].trim();
                        String numeroQuarto = detalhes[2].trim();
                        String dataCheckin = detalhes[3].trim();
                        String dataCheckout = detalhes[4].trim();

                        Map<String, String> detalhesReserva = new HashMap<>();
                        detalhesReserva.put("nome", nomeHospede);
                        detalhesReserva.put("nome_hotel", nomeHotel);
                        detalhesReserva.put("numero_quarto", numeroQuarto);
                        detalhesReserva.put("data_checkin", dataCheckin);
                        detalhesReserva.put("data_checkout", dataCheckout);

                        adicionarReserva(codigoReserva, dataCheckin, detalhesReserva);
                    } else {
                        System.out.println("Formato de linha inválido: " + linha);
                    }
                } else {
                    System.out.println("Formato de linha inválido: " + linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void printHashMapConfiguration() {
        System.out.println("\nConfiguração atual da Hashmap:");

        for (Map.Entry<String, Map<String, String>> entry : reservas.entrySet()) {
            String codigoReserva = entry.getKey();
            Map<String, String> detalhesReserva = entry.getValue();
            System.out.println("\nCódigo da Reserva: " + codigoReserva);
            System.out.println("Detalhes da Reserva: " + detalhesReserva);
            System.out.println("--------------------------------------");
        }

        for (Map.Entry<String, List<Map<String, String>>> entry : listaEspera.entrySet()) {
            String chaveFilaEspera = entry.getKey();
            List<Map<String, String>> reservasEspera = entry.getValue();
            System.out.println("Reservas em lista de espera para: " + chaveFilaEspera);
            for (Map<String, String> reserva : reservasEspera) {
                System.out.println("Detalhes da Reserva: " + reserva);
            }
            System.out.println("--------------------------------------");
        }
    }

    public static void main(String[] args) {
        SistemaReservasHotel sistemaHotel = new SistemaReservasHotel();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nBem vindo ao - Sistema de Reservas -");
            System.out.println("1. Adicionar reserva");
            System.out.println("2. Consultar reserva");
            System.out.println("3. Remover reserva");
            System.out.println("4. Ler reservas de arquivo");
            System.out.println("5. Imprimir configuração atual da Hashmap");
            System.out.println("6. Sair");

            System.out.print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            switch (escolha) {
                case "1":
                    System.out.print("Digite o nome do hóspede: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o nome do hotel: ");
                    String nomeHotel = scanner.nextLine();
                    System.out.print("Digite o número do quarto: ");
                    String numeroQuarto = scanner.nextLine();
                    System.out.print("Digite a data de check-in (AAAA-MM-DD): ");
                    String dataCheckin = scanner.nextLine();
                    System.out.print("Digite a data de check-out (AAAA-MM-DD): ");
                    String dataCheckout = scanner.nextLine();
                    Map<String, String> detalhesReserva = new HashMap<>();
                    detalhesReserva.put("nome", nome);
                    detalhesReserva.put("nome_hotel", nomeHotel);
                    detalhesReserva.put("numero_quarto", numeroQuarto);
                    detalhesReserva.put("data_checkin", dataCheckin);
                    detalhesReserva.put("data_checkout", dataCheckout);
                    String codigoReserva = UUID.randomUUID().toString();
                    sistemaHotel.adicionarReserva(codigoReserva, dataCheckin, detalhesReserva);
                    break;

                case "2":
                    System.out.print("\nDigite o código da reserva: ");
                    String codigo = scanner.nextLine();
                    Map<String, String> reserva = sistemaHotel.obterReserva(codigo);
                    if (reserva != null) {
                        System.out.println("Detalhes da reserva:");
                        System.out.println("Nome do hóspede: " + reserva.get("nome"));
                        System.out.println("Nome do hotel: " + reserva.get("nome_hotel"));
                        System.out.println("Número do quarto: " + reserva.get("numero_quarto"));
                        System.out.println("Data de check-in: " + reserva.get("data_checkin"));
                        System.out.println("Data de check-out: " + reserva.get("data_checkout"));
                    } else {
                        System.out.println("Reserva não encontrada.");
                    }
                    break;

                case "3":
                    System.out.print("Digite o código da reserva a ser removida: ");
                    codigo = scanner.nextLine();
                    sistemaHotel.removerReserva(codigo);
                    break;

                case "4":
                    System.out.print("Digite o nome do arquivo: ");
                    String nomeArquivo = scanner.nextLine();
                    sistemaHotel.lerReservasDeArquivo(nomeArquivo);
                    break;

                case "5":
                    sistemaHotel.printHashMapConfiguration();
                    break;
                case "6":
                    System.out.println("Saindo do sistema de reservas.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}