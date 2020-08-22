## Desafio Mobile - Quem é esse pokemón!

### Instruções gerais
O objetivo deste teste é avaliar seus conhecimentos técnicos no desenvolvimento mobile Android.
O teste consiste em utilizar a PokéAPI V2 para construção de uma Pokédex. Este desafio deve ser
feito por você em sua casa. Você pode levar o tempo que quiser, porém nos informe antes de começar
quanto tempo precisa para realizar o desafio. Fique à vontade para inventar um layout seu ou se
inspirar em algum outro app. Layouts bonitos e com animações entre as transições de telas são muito
bem-vindos! =)


### Instruções de Entrega do Desafio:

    1. Crie um repositório público em seu Github pessoal.

    2. Utilize esse repositório para commitar suas alterações conforme forem sendo implementadas. (evite commitar apenas o código com o desafio todo pronto)

    3. Após terminar nos envie o link do repositório para avaliarmos.

### Requisitos Funcionais do App:

    [x] Tela inicial com lista com todos os Pokémons
    [x] Busca por ID ou Nome utilizando o Search Dialog
    [x] Paginação utilizando Endless Scrolling
    [x] Foto default do Pokémon na listagem
    [X] Tela com os detalhes do Pokémon ○ Nome e ID;
    -   Carrossel com as fotos disponíveis do Pokémon
    [X] View com os stats do Pokémon (hp, attack, defense, special attack, special defense, speed);
    -   Exibir suas Habilidades (Run Away, Adaptability, Synchronize etc);
    -   Ao tocar em uma habilidade, exibir um modal com a descrição;
    -   Exibir seus tipos (electric, ground, water, fire etc);
    -   Ao tocar em um tipo, exibir a lista dos pokemons desse mesmo tipo;
    -   Exibir a cadeia de evolução do Pokémon;
    -   Exibir spinner (combobox) que permita selecionar as variações do Pokémon (ao selecionar uma variação, o app deve carregar automaticamente os dados da variação selecionada);

### Requisitos Técnicos:

    [x] Desenvolva em Java ou Kotlin;
    [x] Utilizar alguma arquitetura de projeto (MVC, MVVM, Clean etc);
    [x] Utilizar Retrofit2 para realizar os request às API's;
    [x] Utilizar algum framework para o mapping do JSON (Jackson, GSON, Moshi etc);
    [x] Tratar erros nas chamadas;
    [x] Não utilizar nenhum wrapper para a Pokémon API;


### Ganha mais pontos se:

    -   Testes unitários e instrumentados;
    [x] Utilizar RxJava ou Coroutines;
    [x] Utilizar Architecture Components ( Navigation, LiveData, Room, ViewModel etc);
    -   Cache das imagens e dos requests para APIs;
    -   Utilizar shared element transitions;
    [x] Suportar mudanças de orientações das telas sem perder o estado;
    -   Seguir as guidelines do Material Design;
    -   Conseguir as imagens dos posters e personagens de alguma API pública;
    [x] Construir layouts com Constraint Layout;
    -   Trabalhar offline (cache dos dados);
    -   Utilizar algum framework de injeção de dependência (dagger, koin, kodein);


### Links Úteis:
    -   PokéAPI - https://pokeapi.co/
    -   Ex. Pokedex - https://www.pokemon.com/br/pokedex/
    -   Pokémon com variações - https://www.pokemon.com/br/pokedex/necrozma
    -   Pokémon com múltiplas evoluções - https://www.pokemon.com/br/pokedex/eevee
    -   Pokémon com Evoluções Bidirecionais - https://www.pokemon.com/br/pokedex/wurmple
    -   Guidelines Material Design - https://material.io/develop/android/
    -   Creating a Search Interface - https://developer.android.com/guide/topics/search/search-dialog