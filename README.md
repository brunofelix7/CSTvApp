# CS Tv
#### Fuze challenge

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

O CS Tv é um aplicativo desenvolvido com o objetivo de listar as partidas de CS:GO que estão prestes a acontecer, ou que já estão ocorrendo. Todas as informações disponibilizadas no app são retornadas da API PandaScore.

### Telas
O app contém uma **SplashScreen** usando **layer-list** no padrão dos apps do Google, dessa forma não foi necessário utilizar uma Activity. Também conta com uma tela de listagem de partidas, detalhes e um layout personalizado de erros.
O app também pussui uma funcionalizada para agendar o horário da partida e enviar uma **Notification** usando o **WorkManager**.

![banner](https://user-images.githubusercontent.com/10690387/178573455-7d68f213-7649-4596-90be-e99c359b136f.png)

### Tecnologias
- Kotlin Language
- StateFlow (LiveData)
- MVVM (Architecture Component)
- Dagger Hilt (Dependency Injection)
- Lifecycle
- Coroutines
- Retrofit
- Glide
- WorkManager
- Notification
- RecyclerView
- ConstraintLayout
- Bottom Sheets
- Themes (Dark / Light)
- SharedPreferences
- Unit Tests
- Integration Tests
- UI Tests (Espresso)

### Gitmoji (commits)
Durante o desenvolvimento foi usado o Gitmoji como padrão para os commits.

| Emoji | Descrição |
| ------ | ------ |
| :tada: | Criação do projeto (primeiro commit) |
| :heavy_plus_sign: | Inclusão de dependências no Gradle |
| :bento: | Inclusão ou atualização de assets |
| :lipstick: | Criação ou atualização de layout de UI |
| :speech_balloon: | Inclusão ou atualização de textos |
| :sparkles: | Implementação de nova feature |
| :wrench: | Inclusão ou atualização de arquivos de configuração |
| :recycle: | Refatoração de código |
| :bug: | Correção de bug |
| :adhesive_bandage: | Correção de erro simples |
| :goal_net: | Tratamento de erros e exceções |
| :white_check_mark: | Criação ou atualização de testes |

### APK

[CSTv.apk](https://drive.google.com/file/d/1pd9hzC1NinjJQ9MwcaI6465p-yfjaVEU/view?usp=sharing/)




