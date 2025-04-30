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
- Pode ser facilmente integrada uma lógica de pagamento
- Estrutura pronta para expansão de funcionalidades (como wishlist, favoritos, carrinho, notificações, etc.)

---

## 📱 Screenshots

> (adicione imagens da tela principal, login, categorias, etc.)

---

## ✅ Status do Projeto

✅ Finalizado o MVP (mínimo produto viável)  
🚀 Pronto para testes externos e publicação na Play Store  

---

## 📄 Licença

Distribuído sob a licença MIT.

---
