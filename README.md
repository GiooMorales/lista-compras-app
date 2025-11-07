# Lista de Compras

Aplicativo Android nativo em Kotlin que permite criar e gerenciar listas de compras sincronizadas com o Firebase Firestore. Foi desenvolvido como exemplo educacional, demonstrando o uso de `RecyclerView`, `Intents` e integração com serviços em nuvem do ecossistema Google.

<img width="320" height="641" alt="image" src="https://github.com/user-attachments/assets/bfcbf6dc-4834-4c05-8d95-1b165fcd8d9a" />


## Visão Geral

- **Plataforma:** Android (minSdk 24, targetSdk 35)
- **Linguagem:** Kotlin
- **Arquitetura:** Activities tradicionais com `RecyclerView`
- **Persistência:** Firebase Cloud Firestore (tempo real)

O aplicativo exibe uma lista de itens cadastrados no Firestore (`tasks`) e permite inserir novos itens por meio de um formulário dedicado.

## Funcionalidades

- Listagem de itens de compra com título e descrição.
- Sincronização em tempo real com a coleção `tasks` do Firestore.
- Formulário para inclusão de novos itens.
- Navegação entre telas via `Intent`.
- Layouts responsivos com `ConstraintLayout` e `LinearLayout`.

## Capturas de Tela (sugestão)

Inclua imagens na pasta `docs/images` e atualize os links abaixo.

```
docs/images/lista.png
docs/images/formulario.png
```

## Tecnologias e Dependências

- Kotlin 1.9+
- AndroidX (`core-ktx`, `appcompat`, `activity`, `constraintlayout`, `RecyclerView`)
- Material Design Components
- Firebase BoM e `firebase-firestore`
- Gradle 8 / AGP 8 (via `libs.versions.toml`)

## Pré-requisitos

- Android Studio Iguana (ou superior)
- Conta Firebase com acesso ao Firestore
- Dispositivo ou emulador com Android 7.0 (API 24) ou superior

## Configuração do Projeto

1. **Clonar o repositório**
   ```bash
   git clone https://github.com/<usuario>/lista-compras-app.git
   cd lista-compras-app
   ```
2. **Abrir no Android Studio** e aguardar o `Gradle Sync`.
3. **Selecionar a variante `debug`** (padrão) ou configurar a desejada.
4. **Executar** em um emulador ou dispositivo conectado (`Run > Run 'app'`).

## Configurando o Firebase

1. Crie um projeto no [Firebase Console](https://console.firebase.google.com/).
2. Adicione um aplicativo Android usando o `applicationId` `br.gov.sp.etec.lista`.
3. Baixe o arquivo `google-services.json` e substitua o existente em `app/google-services.json`.
4. Ative o Firestore no modo de produção ou teste. Para desenvolvimento, você pode iniciar com uma regra permissiva temporária (não use em produção):
   ```
   rules_version = '2';
   service cloud.firestore {
     match /databases/{database}/documents {
       match /{document=**} {
         allow read, write: if request.time < timestamp.date(2025, 1, 1);
       }
     }
   }
   ```
5. Crie a coleção `tasks` com documentos contendo os campos:
   - `title` (string)
   - `description` (string)
   - `status` (string, ex.: `PENDING`)

## Estrutura do Código

```
app/
├─ src/main/java/br/gov/sp/etec/lista/
│  ├─ UI/
│  │  ├─ MainActivity.kt        # Lista itens do Firestore
│  │  └─ ItemForm.kt            # Formulário de criação/edição
│  ├─ adapters/
│  │  └─ TaskAdapter.kt         # Binder RecyclerView
│  └─ models/
│     └─ Task.kt                # Modelo de dados
├─ src/main/res/layout/         # Layouts XML (lista, item, formulário)
└─ build.gradle.kts             # Configurações do módulo app
```

## Fluxo Principal

1. `MainActivity` conecta-se ao Firestore, busca a coleção `tasks` e atualiza o `RecyclerView` via `TaskAdapter`.
2. Ao tocar em um item, abre-se `ItemForm` preenchida com os dados (edição futura).
3. No formulário, o botão `Salvar` envia um novo documento para o Firestore.

## Build e Scripts Úteis

- `./gradlew assembleDebug` — gera o APK de debug em `app/build/outputs/apk/debug/`.
- `./gradlew test` — executa testes unitários (nenhum incluso por padrão).
- `./gradlew connectedAndroidTest` — executa testes instrumentados (requer dispositivo).

## Testes

Atualmente o projeto possui apenas os alicerces para testes (`androidTest` e `test`). Sugestões:

- Criar testes instrumentados para validar o fluxo de criação de itens com `Espresso`.
- Adicionar testes unitários para validar formatação/validação de dados antes de enviar ao Firestore.

## Roadmap Sugerido

- Implementar atualização e exclusão de itens pelo formulário.
- Exibir estados (`status`) e permitir marcar itens como concluídos.
- Adicionar autenticação (ex.: Firebase Authentication) para listas personalizadas.
- Melhorar o design dos layouts com Material Design 3.
- Criar uma camada de repositório para isolar o acesso ao Firestore.

## Contribuição

1. Faça um fork do repositório.
2. Crie uma branch para sua feature: `git checkout -b feature/nome`.
3. Commit suas alterações: `git commit -m "feat: ..."`.
4. Envie a branch: `git push origin feature/nome`.
5. Abra um Pull Request descrevendo as mudanças.

## Licença

Defina aqui a licença do projeto (ex.: MIT, Apache 2.0). Caso ainda não exista, adicione um arquivo `LICENSE` com o texto completo.
