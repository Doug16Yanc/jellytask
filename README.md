# To-Do List Kotlin/JS - Jellytask

Este repositório contém a versão Kotlin/JS de um aplicativo de lista de tarefas (Jellytask), desenvolvido como parte do Desafio Prático da Unidade III - Linguagens relacionadas ao JavaScript.

## Visão Geral

Aplicativo simples de lista de tarefas (To-Do List) implementado em Kotlin/JS. Permite ao usuário:

* Adicionar novas tarefas com descrição
* Mover tarefas para em andamento
* Mover tarefas para concluídas
* Exibir dashboard de tarefas

A interface utiliza HTML e CSS para estrutura e estilo, enquanto a lógica e manipulação do DOM são escritas em Kotlin compilado para JavaScript.

## Funcionalidades

1. **Adicionar Tarefa**: Insira uma descrição e adicione à lista.
2. **Mover para Em adamento**: Arrastar para mover tarefa para concluída.
3. **Mover para Concluída**: Arrastar para mover tarefa para concluída.
4. **Exibir dashboard de tarefas**: Exibe a quantidade de tarefas e informações importantes.

## Tecnologias

* **Kotlin/JS**: Linguagem principal para lógica e manipulação do DOM.
* **Gradle**: Gerenciamento de dependências e build.
* **HTML5 & CSS3**: Estrutura e estilo da página.

## Estrutura do Projeto

```plaintext
├── build.gradle.kts       # Configuração Kotlin/JS via Gradle
├── settings.gradle.kts    # Configuração do projeto
└── jsMain
    ├── kotlin
    │   └── app
    │       ├── Main.kt
    │       ├── model
    │       │   ├── enums
    │       │   │   ├── Identity.kt
    │       │   │   ├── Priority.kt
    │       │   │   └── Status.kt
    │       │   ├── Task.kt
    │       │   └── User.kt
    │       ├── repository
    │       │   ├── TaskRepository.kt
    │       │   └── UserRepository.kt
    │       ├── service
    │       │   ├── DashboardService.kt
    │       │   ├── TaskService.kt
    │       │   └── UserService.kt
    │       ├── utils
    │       │   ├── AlertDialog.kt
    │       │   ├── external
    │       │   │   ├── Chart.kt
    │       │   │   └── Sortable.kt
    │       │   ├── FormatDate.kt
    │       │   ├── Graphics.kt
    │       │   ├── JSONUtils.kt
    │       │   └── SetupSortable.kt
    │       └── viewmodel
    │           ├── DashboardViewModel.kt
    │           ├── LoginViewModel.kt
    │           ├── TaskViewModel.kt
    │           └── UserViewModel.kt
    └── resources
        ├── css
        │   ├── calendar.css
        │   ├── dashboard.css
        │   ├── legends.css
        │   ├── login.css
        │   ├── navbar.css
        │   ├── sidebar.css
        │   └── team.css
        ├── images
        │   ├── icon.png
        │   └── jellyfish.jpg
        ├── index.html
        ├── pages
        │   ├── dashboard.html
        │   ├── home.html
        │   └── team.html
        └── styles.css
```

## Pré-requisitos

* JDK 11 ou superior
* Gradle (wrapper incluído)
* Navegador compatível com JavaScript

## Setup e Execução

1. Clone o repositório:

   ```bash
   git clone https://github.com/Doug16Yanc/jellytask.git
   cd jellytask
   ```

````
2. Execute:
   ```bash
 ./gradlew jsBrowserDevelopmentRun --continuous
````

3. Abra `http://localhost:8080` em seu navegador.

## Justificativa da Linguagem

Kotlin/JS foi escolhido para explorar a capacidade de escrever aplicações web usando Kotlin, aproveitando:

* **Data Classes**: para modelar tarefas de forma concisa.
* **Null Safety**: evitando erros comuns de `null`.
* **Interoperabilidade com JavaScript**: acesso direto ao DOM.

## Pontos Positivos e Dificuldades

| Aspecto                 | Positivos                                                           | Dificuldades                                   |
| ----------------------- | ------------------------------------------------------------------- | ---------------------------------------------- |
| Sintaxe                 | Mais expressiva, data classes reduzem boilerplate                   | Verbosidade ao usar APIs do DOM                |
| Ferramentas de Build    | Gradle facilita configuração e plugins                              | Configuração inicial do Kotlin/JS no Gradle    |
| Integração com HTML/CSS | Uso de `kotlinx.browser.document` torna manipulação direta e tipada | Mapear corretamente seletores e eventos do DOM |

## Screenshots
<img width="1920" height="966" alt="Image" src="https://github.com/user-attachments/assets/703d1d25-5148-43f4-861f-50254f3843d4" />
