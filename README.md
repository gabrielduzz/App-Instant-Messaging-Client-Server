# App de Mensagens Instantâneas (Cliente-Servidor)

Este projeto é um sistema de chat robusto que simula o funcionamento de aplicações como WhatsApp ou Telegram, focado na eficiência da comunicação em rede. 

## 🚀 Sobre o Projeto

A aplicação utiliza uma abordagem híbrida de protocolos da camada de transporte para otimizar diferentes tipos de tráfego:
- **TCP (Transmission Control Protocol):** Utilizado para operações que exigem fiabilidade, como entrar em grupos (`JOIN`) ou sair deles (`LEAVE`).
-**UDP (User Datagram Protocol):** Utilizado para o envio de mensagens (`SEND`), garantindo baixa latência na comunicação entre os membros do grupo.

## 🛠️ Funcionalidades

- **Arquitetura Cliente-Servidor:** Servidor centralizado que gere a lógica de grupos e distribuição de mensagens.
- **Gestão de Grupos:** Capacidade de criar e gerir múltiplos grupos simultâneos.
- **Protocolo de Aplicação (APDU):** Implementação de uma unidade de dados de protocolo própria para padronizar a comunicação entre cliente e servidor.
- **Interface Gráfica (GUI):** Interface intuitiva desenvolvida em JavaFX para utilizadores e monitorização do servidor.
- **Execução em Rede Real:** Preparado para funcionar em ambientes de rede local (LAN) com endereçamento IP real.

## 📂 Estrutura do Protocolo (APDUs)

O sistema comunica através de comandos específicos:
- `JOIN(grupo, usuario)`: Regista um utilizador num grupo específico via TCP.
- `SEND(grupo, usuario, mensagem)`: Distribui a mensagem para todos os membros do grupo via UDP.
- `LEAVE(grupo, usuario)`: Remove o utilizador do grupo via TCP.

## 💻 Tecnologias Utilizadas

- **Linguagem:** Java
- **Redes:** Sockets (TCP/UDP)
- **Interface:** JavaFX (FXML)
- **Concorrência:** Threads para manipulação de múltiplos clientes simultâneos no servidor.
