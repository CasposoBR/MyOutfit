# 👕 MyOutfit

**MyOutfit** é um aplicativo Android desenvolvido com o objetivo de ajudar os usuários a descobrirem roupas estilosas e populares que muitas vezes não são facilmente encontradas por filtros tradicionais. Todas as peças estão disponíveis com links diretos de compra (como da Shein), categorizadas por tipo e estilo.

---

## 🎯 Objetivo

Facilitar a descoberta de roupas em alta no mercado, agrupando-as por categorias e estilos que refletem tendências reais como **"Urbano"**, **"Old Money"**, **"Star Boy"**, entre outros.

---

## 🧩 Funcionalidades

- 🔐 Autenticação de usuários com **Firebase**:
  - Login com e-mail e senha
  - Cadastro de novo usuário
  - Login com conta **Google**

- 📦 Banco de dados local com **Room**:
  - Armazena e filtra as peças de roupa.
  - Adiciona cada peça de roupa na lista de favoritos.
  - Organiza por **categoria** (camisa, short, calça, etc.)
  - Filtro por **estilo** (urbano, clássico, etc.)

- ✅ Testes unitários:
  - Verificam se o Room está persistindo corretamente os dados
  - Testam se a autenticação com o Firebase está funcional
  - Testam o carregamento das roupas por meio da classe `ClothingItem`

---

## 🧱 Arquitetura

- 🧠 MVVM (Model-View-ViewModel)
- 🛠️ Designs Pattern (dark mode e white mode)
- 🔀 Navegação com Navigation Compose 
- 📦 Injeção de dependência com **Hilt**
- 🛠️ Jetpack Compose para interface 
- 🧪 Testes com ambiente configurado para Room e Firebase

---

## 🛠️ Tecnologias utilizadas

- Kotlin `2.1.0`
- Firebase Authentication
- Room Database
- Jetpack Compose
- Hilt (Dependency Injection)
- Google Sign-In
- AdSense (para monetização futura)
- Retrofit (para consumo de APIs)
- Material 3

---

## 📈 Escalabilidade

O MyOutfit foi pensado com foco em **escalabilidade**:

- Banco de dados pode ser trocado por um mais robusto no futuro (como Firestore ou PostgreSQL via API REST)
- Pode ser facilmente integrada uma lógica de pagamento.
- Estrutura pronta para expansão de funcionalidades (como wishlist, carrinho, notificações, etc.)

---

## 📱 Screenshots

![Captura de tela 2025-04-30 005450](https://github.com/user-attachments/assets/7b2228ef-d078-434c-abfa-dda1ee49224d) // TELA DE CADASTRO
![Captura de tela 2025-04-30 005719](https://github.com/user-attachments/assets/0282c8e7-634a-4cb9-9a23-d89a7c2bda12) // HOME SCREEN
![Captura de tela 2025-04-30 005942](https://github.com/user-attachments/assets/29df50d5-6b1a-49ec-bae4-4bd401d17319) // ARQUIVOS 
![Captura de tela 2025-04-30 010013](https://github.com/user-attachments/assets/bc44fcd9-ae41-4918-b583-ad0d2fb63d09) // AMBIENTE DE TESTES
![Captura de tela 2025-04-25 234129](https://github.com/user-attachments/assets/2d581397-b0e8-4790-8dd1-bf6eefe8cc7b) // TESTE PARA VER SE AS PEÇAS ESTAVAM SENDO ADICIONADAS OU NÃO.
![Captura de tela 2025-04-25 233521](https://github.com/user-attachments/assets/24f2e0cc-d83e-4bf3-a325-202adff0042c) // TESTES PARA VER SE O SOFTWARE RECEBE E ATUALIZA AS PEÇAS DE ROUPA AO ATUALIZAR BANCO DE DADOS.
![Captura de tela 2025-04-26 004334](https://github.com/user-attachments/assets/cde0347c-1efe-489c-9a00-c641069d2f10) // TESTES PARA VER SE AS PEÇAS DE ROUPAS ESTAVAM SENDO FAVORITADAS. 
![Captura de tela 2025-04-30 005739](https://github.com/user-attachments/assets/1e02621e-404f-484b-a139-79dd42ab571f) // TELA DE FAVORITOS


## ✅ Status do Projeto

✅ Finalizado o MVP (mínimo produto viável)  
🚀 Pronto para testes externos e publicação na Play Store  

---

## 📄 Licença

Distribuído sob a licença MIT.

---
