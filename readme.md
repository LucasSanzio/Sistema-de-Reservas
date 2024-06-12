# **SISTEMA DE RESERVAS**

## **Alunos:**
- **Lucas Ribeiro Moreira**
  - **RA:** 123112959
- **Augusto Dias Moura Macedo**
  - **RA:** 123112822

## **Professor:**
- **Marco Antônio**

## **Descrição do Projeto:**

Este código implementa um Sistema de Reservas de Hotel em Java. Ele permite adicionar, consultar e remover reservas, além de ler informações de reservas de um arquivo e imprimir a configuração atual do sistema.

## **Funcionalidades Principais:**

1. **Adicionar Reserva:**
   - Permite adicionar uma reserva de hotel, fornecendo o nome do hóspede, nome do hotel, número do quarto, data de check-in e data de check-out.
   - Se o quarto estiver disponível para a data fornecida, a reserva é adicionada com sucesso.
   - Caso contrário, a reserva é adicionada à lista de espera.

2. **Consultar Reserva:**
   - Permite consultar os detalhes de uma reserva específica, fornecendo seu código de reserva.
   - Se a reserva existir, os detalhes do hóspede, hotel, quarto e datas de check-in e check-out são exibidos.

3. **Remover Reserva:**
   - Permite remover uma reserva existente, fornecendo seu código de reserva.
   - Se a reserva for encontrada, ela é removida do sistema.
   - Se houver reservas na lista de espera para a mesma data e quarto, a primeira reserva da lista de espera é promovida para ativa.

4. **Ler Reservas de Arquivo:**
   - Permite ler as informações de reserva de um arquivo, seguindo um formato específico.
   - As reservas são adicionadas ao sistema uma por uma.

5. **Imprimir Configuração Atual da Hashmap:**
   - Exibe a configuração atual do sistema, incluindo todas as reservas ativas e as reservas na lista de espera.
   - Mostra os detalhes de cada reserva e as informações da lista de espera, se houver.

O sistema é interativo e funciona em um loop contínuo, permitindo ao usuário escolher entre as diferentes opções de funcionalidade.
