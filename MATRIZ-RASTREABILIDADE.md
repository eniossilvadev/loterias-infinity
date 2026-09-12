# Matriz de rastreabilidade — Operação Legadão

> Versão 1 · criada na fase **F0** (salvaguarda e linha de base) · 2026-09-12
>
> Princípio: nenhuma funcionalidade do legado é descartada por omissão. Cada
> linha abaixo tem um destino explícito. Uma linha só pode ser marcada como
> **✅ Concluído** quando a estratégia/utilitário correspondente estiver
> homologado na plataforma nova (F3/F4) ou formalmente arquivado com
> justificativa (F6). Ver o relatório completo: [Operação Legadão](https://claude.ai/code/artifact/c885ae6d-c796-4786-b6fb-2c10e8221e46).
>
> **Destinos possíveis:**
> - **Migrar** — vira estratégia/perfil/componente na plataforma nova.
> - **Absorver** — a funcionalidade sobrevive, mas fundida num componente
>   único junto com suas duplicatas (ver seção 08 do relatório).
> - **Arquivar** — congelado como referência histórica; não migra (código
>   morto, duplicata exata, ou substituído por solução já existente e melhor
>   — ex. API oficial no lugar do parser HTML).
> - **Corrigir e migrar** — bug conhecido; a correção é decidida e registrada
>   no ato da migração, não silenciosamente.

Legenda de status: 🔲 Pendente · 🟡 Em andamento · ✅ Concluído

---

## 1. Estratégias de geração — Lotofácil

| Classe / FQN | Loteria | Destino | Status | Notas |
|---|---|---|---|---|
| `cliente.geradores.lotofacil.SuperLotofacil2026` | Lotofácil | Migrar (onda 1) | 🔲 | Flagship atual — pool 360k, filtro 9–12 vs. último sorteio, janela 150–900 |
| `cliente.geradores.lotofacil.LotofacilInverte` | Lotofácil | Migrar (onda 1) | 🔲 | WIP não commitado — confirmar com o autor se está pronto p/ referência |
| `cliente.geradores.lotofacil.LotofacilFullUIltimoConcursoQuadrante` | Lotofácil | Migrar (onda 3) | 🔲 | Foco em repetir 11 dezenas do último concurso |
| `cliente.geradores.lotofacil.LotofacilUIltimoConcursoQuadrante` | Lotofácil | Absorver | 🔲 | Duplicata da linha acima (10 dezenas + rotação de configs) |
| `cliente.geradores.lotofacil.GerarLotofacilRandomSoInclPremiadosFull` | Lotofácil | Migrar (onda 3) | 🔲 | Inclusão só de dezenas de jogos premiados |
| `cliente.geradores.lotofacil.Lotofacil2024InvertFromList` | Lotofácil | Absorver | 🔲 | Família de 4 variantes (`FromList`, `FromListVarList`, `FullList`, `Sublist`) — vira 1 estratégia parametrizada por origem da lista |
| `cliente.geradores.lotofacil.Lotofacil2024InvertFullList` | Lotofácil | Absorver | 🔲 | Idem — usa path pessoal `C:\Users\enios\Downloads\original.txt`, remover hardcode |
| `cliente.geradores.lotofacil.SimpleLotofacilFullNovoLoopInverteLoopLastQuadranteNew` | Lotofácil | Arquivar | 🔲 | Wrapper descartável sobre entrada fixa de mar/2024 |
| `cliente.geradores.lotofacil.LotofacilNovoLoopInverteLoopLastQuadranteNew` | Lotofácil | Absorver | 🔲 | Família de 5 variantes "NovoLoop...Quadrante*" — mesma estratégia, config variável |
| `cliente.geradores.lotofacil.LotofacilNovoLoopInverteLoopLastQuadrante` | Lotofácil | Absorver | 🔲 | Idem |
| `cliente.geradores.lotofacil.LotofacilNovoLoopInverteLoopLast` | Lotofácil | Absorver | 🔲 | Idem, sem quadrantes |
| `cliente.lotofacil.current.GerarLotofacilNovoLoopInverteLoopLastMaisDe15` | Lotofácil | Migrar (onda 3) | 🔲 | Config18 (18 dezenas) |
| `cliente.lotomania.LotofacilFullNovoLoopInverteLoopLastQuadranteNew` | Lotofácil | Corrigir e migrar | 🔲 | **Mal alocada** no pacote `lotomania` — mover pacote é parte da correção |
| `cliente.lotofacil.current.*EsquemaXisDoTikTok*` (4 classes) | Lotofácil | Migrar (onda 3) | 🔲 | Esquema fechado combinatório — decisão: manter como perfil experimental opt-in |
| `cliente.lotofacil.esquemas.Esquema*` (Lotofácil, Lotomania, Random5Por3, Vencendo) | Lotofácil/Lotomania | Migrar (onda 3) | 🔲 | Desdobramentos fechados (19n11a, 19n24j, 21n21a, "Jacqueline") |
| `cliente.lotofacil.GerarJogosLotofacil202007_*` (5 classes) | Lotofácil | Arquivar | 🔲 | Geração de 2020, superada pelas famílias mais novas |
| `cliente.lotofacil.deprecated.*` (18 classes) | Lotofácil | Arquivar (⚠️ exceto 2) | 🔲 | **`GerarJogosLotofacil2020Ab` e `GerarJogosLotofacilAb` são infraestrutura viva** — extrair antes de arquivar o resto |
| `config.LotomaniaFullNew2023` | Lotofácil | Corrigir e migrar | 🔲 | Nome/pacote enganoso — é gerador de Lotofácil |
| Demais `cliente.lotofacil.current.*` (frequência, random, loops) | Lotofácil | Arquivar | 🔲 | ~14 classes legadas sem uso ativo confirmado — revisar 1x antes de arquivar |

## 2. Estratégias de geração — Mega-Sena

| Classe / FQN | Destino | Status | Notas |
|---|---|---|---|
| `cliente.geradores.megasena.SuperMega2026` | Migrar (onda 1) | 🔲 | Bolão de 12 dezenas |
| `cliente.geradores.megasena.SuperMega2025` | Migrar (onda 1) | 🔲 | 20 jogos de 6 dezenas |
| `cliente.geradores.megasena.MegaFullLoopExcluirIncluirTopezaMaisDe6MaisMenos` | Migrar (onda 3) | 🔲 | Referência de `paths.md` — topeza + mais/menos |
| `cliente.geradores.megasena.MegaLoopExcluirIncluirTopezaMaisDe5_bkp` | Arquivar | 🔲 | Backup explícito (`_bkp`) |
| `cliente.geradores.megasena.MegaLoopExcluirIncluirTopezaSuper08` | Migrar (onda 2) | 🔲 | Variante sazonal/bolão |
| `cliente.geradores.megasena.GerarJogosMegaDez` | Migrar (onda 2) | 🔲 | Bolão de 10 dezenas |
| `cliente.geradores.mega20.MegaLoopExcluirIncluirTopezaSuper20` | Migrar (onda 2) | 🔲 | Bolão de 20 dezenas |
| `cliente.megasena.GerarJogosMegaLoop*` (13 classes, incl. `TopezaVirada`) | Absorver / Migrar (onda 2) | 🔲 | `TopezaVirada` = Mega da Virada, migra como perfil sazonal; resto absorve na estratégia topeza única |
| `cliente.mix.MegaDaVirada` | Migrar (onda 2) | 🔲 | Consolidador sazonal de arquivos |
| `gerador.GerarMegaLoopExcluirIncluirRandom` | Arquivar | 🔲 | Duplicata mais antiga |

## 3. Estratégias de geração — Quina

| Classe / FQN | Destino | Status | Notas |
|---|---|---|---|
| `cliente.geradores.quina.QuinaQuadranteBaseN` | Migrar (onda 1) | 🔲 | Versão mais limpa, referência de `paths.md` |
| `cliente.geradores.quina.QuinaQuadranteBase2025N` | Migrar (onda 1) | 🔲 | WIP não commitado |
| `cliente.geradores.quina.QuinaComBaseFixa` | Migrar (onda 1) | 🔲 | Referência de `paths.md` |
| `cliente.geradores.quina.QuinaComBaseMovel` | Migrar (onda 1) | 🔲 | Base móvel |
| `cliente.geradores.quina.QuinaLoopExcluirIncluirPreJogo` | Migrar (onda 1) | 🔲 | Referência de `paths.md` |
| `cliente.geradores.quina.NovoBingoDaSorte` / `NovoBingoDaSorte_1` | Absorver | 🔲 | Reaproveitam Quina p/ Bingo — unificar com motor do Bingo |
| `cliente.geradores.SuperQuinaSaoJoao` | Migrar (onda 2) | 🔲 | Sazonal |
| `cliente.quina.GerarJogosQuinaLoopExcluirIncluir*` (9 classes) | Absorver | 🔲 | Uma classe por tamanho de bolão → 1 componente parametrizado por tamanho |
| `cliente.quina.GerarJogosQuinaLoopExcluirIncluirGenerico10` | Migrar (infra) | 🔲 | ⚠️ Usada como biblioteca pelo motor do Bingo — não arquivar isoladamente |
| `cliente.quina.GerarJogosQuina2023`, `GerarQuinaRandomSoInclPremiados`, `BaseQuina` | Arquivar | 🔲 | Legadas sem uso confirmado |
| `gerador.GerarJogosQuina`, `GerarJogosQuinaLoop`, `GerarJogosQuinaLoop2`, `GerarQuinaRandom` | Arquivar | 🔲 | Camada mais antiga, duplicada |

## 4. Estratégias de geração — Lotomania

| Classe / FQN | Destino | Status | Notas |
|---|---|---|---|
| `cliente.lotomania.LotomaniaFullPremiado` | Migrar (onda 1) | 🔲 | Referência de `paths.md` — `PREMIADO=35` |
| `cliente.lotomania.GerarLotomaniaMain` (+ `New`/`NewXTimes`/`Random`/`RandomXTimes`/`NewPremiados`) | Absorver | 🔲 | 6 variações incrementais → 1 componente parametrizado |
| `cliente.frequencia.CalcularFrequenciaLotomania` | Migrar (onda 3) | 🔲 | Top/bottom por frequência — paths pessoais em `D:\Meus Documentos\` |
| `cliente.temp.GerarEspelhosLotomanis`, `GerarJogoLotomaniaComplemento` | Migrar (onda 3) | 🔲 | Espelho/complemento 0–99 |
| `commons-loterias.controller.LotomaniaRN.aplicarFiltro/aplicarFiltro2` | Migrar (F2) | 🔲 | Filtros de repetição (7–10) e par/ímpar (≤2) — únicos filtros de negócio reais fora do pacote `filtro/` |

## 5. Estratégias de geração — Dupla Sena, Timemania, Bingo da Sorte

| Classe / FQN | Loteria | Destino | Status | Notas |
|---|---|---|---|---|
| `cliente.geradores.duplasena.GerarJogosDuplaSena2024` | Dupla Sena | Migrar (onda 1) | 🔲 | Referência de `paths.md` |
| `gerador.GerarJogosDuplaLoop` / `...EqualizaMaisMenos` | Dupla Sena | Absorver | 🔲 | |
| `cliente.timemania.GerarJogosTimemaniaLoopExcluirIncluir10` | Timemania | Migrar (onda 3) | 🔲 | Único gerador — depende de F1 (fechar gap de histórico) |
| `cliente.geradores.SuperBingoDaSorte2024` | Bingo da Sorte | Migrar (onda 1) | 🔲 | Referência de `paths.md` |
| `cliente.geradores.SuperBingoDaSorte2024Iterate` | Bingo da Sorte | Absorver | 🔲 | Duplicata (`QUANTIDADE_DE_JOGOS=1`) do item acima |
| `cliente.geradores.SuperBingoDaSorte` | Bingo da Sorte | Arquivar | 🔲 | Superada pela versão 2024 |
| `cliente.bingodasorte.gerador.GeradorGrupoBingoDaSorte` (+ variantes Novo/NovoComLista/Quadrante) | Bingo da Sorte | Absorver | 🔲 | Motor de 6 grupos temáticos — consolidar variantes num único motor parametrizado |
| `cliente.bingodasorte.newp.Novo01..Novo08` | Bingo da Sorte | Arquivar | 🔲 | Abandonadas (bases de 2022/2023) — lógica já coberta pelo motor de grupos |
| `cliente.geradores.quina.NovoBingoDaSorte(_1)`, `BingoDaSorteQuadranteBaseN(FromList)` | Bingo da Sorte | Absorver | 🔲 | Ver linha da Quina acima |
| `cliente.bingodasorte.gen.*`, `cliente.bingodasorte.ModeloBingoDaSorte` | Bingo da Sorte | Arquivar | 🔲 | Modelagem paralela/duplicada, classe de 7 linhas |
| Cobertura de dados (Dupla Sena / Timemania / Bingo) | — | **Bloqueador de F1** | 🔲 | Nenhuma base histórica moderna cobre essas 3 — pré-requisito antes de migrar os geradores |

## 6. Peças transversais (filtros, pontuadores, pós-processadores)

| Componente | Destino | Status | Notas |
|---|---|---|---|
| 23 classes em `filtro/*` | Migrar (F2) | 🔲 | Cadeia de filtros — vira lista componível na plataforma nova |
| ~45 classes de pontuador (`pontuador/*`, `config/pontuador/*`, `duplasena/*`, `pontuador/lotomania/*`) | Migrar (F2) | 🔲 | Tabelas acertos→pontos — viram dados de config, não código |
| `ShiftBy` | Migrar (F2) | 🔲 | Pós-processador — shift circular |
| `ShiftByNew` | Arquivar | 🔲 | Duplicata byte a byte de `ShiftBy` |
| `PositionalReplacement` | Migrar (F2) | 🔲 | Pós-processador — substituição posicional |
| `PositionalReplacementNew` | Arquivar | 🔲 | Duplicata byte a byte |
| `Quadrantes*` (Lotofácil/Mega/Quina/DuplaSena/Lotomania) + `Rotacao` | Migrar (F2) | 🔲 | Motor de geração por quadrantes — 1 implementação parametrizada por loteria |
| `gerador.Base`, `GerarJogosBase`, `GerarJogosDuplaBase` | Migrar (F2) | 🔲 | Superclasses comuns — viram a base do pipeline novo |
| `conferator/*` (11 classes) | Migrar (F4) | 🔲 | Conferência v2 — mais completa, vira o conferidor único |
| `conferencia/*` (4 classes) | Arquivar | 🔲 | Conferência v1, superada por `conferator/*` |
| `commons-loterias.controller.ConferirRN` | Corrigir e migrar (F4) | 🔲 | Bug: retorna `null` em concurso inexistente → NPE a jusante |
| `commons-loterias.controller.Estatisticas` | Corrigir e migrar (F4) | 🔲 | Bug: possível `ArrayIndexOutOfBounds` em `calcularNumerosRepetidosSorteioAnterior` |
| `lotomania.PontuadorMega*` vs `config.pontuador.PontuadorMega*` | Absorver | 🔲 | Duplicados em dois pacotes — manter 1 |

## 7. Núcleo combinatório e utilitários (ver também seção 08 do relatório — mapa de→para)

| Componente | Destino | Status | Notas |
|---|---|---|---|
| `commons-core math.CombinationUtils` | Migrar (F2) | 🔲 | Motor C(n,k) principal — vira o componente único |
| `super-loterias-2025 util.CombinationUtils` | Absorver | 🔲 | Duplicata do módulo irmão |
| `commons-core math.Combination` | Arquivar | 🔲 | Bugada — ignora parâmetros, `main` de demo |
| `commons-core download.Combination` | Arquivar | 🔲 | Duplicata byte a byte de `math.Combination` |
| `commons-core math.DesdobramentoUtil` | Migrar (F2) | 🔲 | ~330 de 482 linhas são dados de teste — viram testes de verdade |
| `commons-core download.DesdobramentoUtil` | Arquivar | 🔲 | Duplicata byte a byte |
| `arquivo.ArquivoUtil` (715 linhas) | Corrigir e migrar (F2) | 🔲 | Bug: leitura cria arquivo/diretório se não existir; `\r\n` fixo na escrita vs. `line.separator` na leitura |
| `download.ArquivoUtil` | Arquivar | 🔲 | Versão antiga, usada só por `ZipUtil` |
| `ArquivoLeitorUtil` / `ArquivoEscritorUtil` | Migrar (F2) | 🔲 | Reescrita moderna (NIO, UTF-8) já existe — ironicamente pouco usada; deve virar a base |
| `commons-loterias.util.ListUtil` (74 imports) | Corrigir e migrar (F2) | 🔲 | **Bug confirmado:** parâmetro `incluir` é ignorado em `completarExcluirIncluir` — decidir e registrar se a correção muda comportamento observável |
| `commons-loterias.util.ListUtil2` | Arquivar | 🔲 | Versão antiga morta, contém loop vazio e possível loop infinito |
| `commons-core util.ListaUtils` | Absorver | 🔲 | `iterateStream` duplicado com `ListUtil` |
| `commons-loterias.util.LotoUtil` | Arquivar | 🔲 | Código morto — `maiorSequencia` retorna `null` |
| `commons-loterias.util.MathUtil` | Migrar (F2) | 🔲 | Paridade — reescrever em Stream Java 8+ no lugar de commons-collections 3.x |
| `commons-loterias.util.ImprimirUtil` | Arquivar/Reescrever | 🔲 | Estado estático mutável — vira formatador puro |
| `commons-core util.ZipUtil` | Migrar (F1, se necessário) | 🔲 | Só relevante se algum fluxo de ZIP sobreviver; provavelmente obsoleto após API |
| `commons-core properties.PropertiesUtil` + `download.PropertiesUtil` | Arquivar | 🔲 | Carregam `config.properties` inexistente — substituídos pelo catálogo de parâmetros declarativo |

## 8. Mappers e parsing HTML (candidatos fortes a arquivamento total)

| Componente | Destino | Status | Notas |
|---|---|---|---|
| `model.mapper.GenericMapper` | Arquivar | 🔲 | Parsing HTML por offsets mágicos, sem teste; grava debug em `E:\loterias\retorno.txt`. **Substituído pela ingestão via API (F1)** |
| `model.mapper.*Mapper` (Lotofácil, Mega, Quina, Timemania, Lotomania, DuplaSena) | Arquivar | 🔲 | Todos dependem do `GenericMapper` acima |
| `commons-loterias.controller.DuplaSenaRN` / `RNS1` / `RNS2` | Corrigir e arquivar | 🔲 | Bug confirmado: usa `LotofacilMapper` por engano; `S1`/`S2` retornam o mesmo resultado — registrar antes de descartar (pode ter mascarado comportamento em produção) |
| `controller.LotofacilRN` / `MegaSenalRN` / `QuinaRN` / `TimemaniaRN` | Arquivar (2 já mortas) | 🔲 | `MegaSenalRN` e `QuinaRN` já sem nenhuma referência hoje |
| `controller.CopyOfLotofacilRN` | Arquivar | 🔲 | Zero referências, `aplicarFiltro2` com corpo vazio |
| `util.ZipUtil` (parsing) + `.zip` da Caixa | Arquivar | 🔲 | Fluxo de download manual + unzip morre com a API |

## 9. Limpeza garantida (zero referências — ver seção 07 do relatório)

| Item | Ação | Status |
|---|---|---|
| Módulo `commons` (vazio, só `pom.xml`) | Remover | 🔲 |
| `commons-core` download/{Combination, DesdobramentoUtil, PropertiesUtil} | Remover | 🔲 |
| `commons-core` math/{Combination, DesdobramentoUtil}, properties/PropertiesUtil | Remover | 🔲 |
| `commons-loterias`: `CopyOfLotofacilRN`, `MegaSenalRN`, `QuinaRN`, `ListUtil2`, `LotoUtil` | Remover | 🔲 |
| `super-loterias-2025`: `ShiftByNew`, `PositionalReplacementNew`, `ListOfListComparator2`, `Constantes` (enum vazio), `GeradorBingoDaSorteIf` (interface vazia) | Remover | 🔲 |
| `.class` órfãos em `src/main/java/.../money/lotomania/` | Remover | 🔲 |
| `src/tmp.xml` (0 bytes), `src/main/resources/temp.json` (nunca lido) | Remover | 🔲 |
| Pasta `testes/` (não compila como teste JUnit) | Remover ou mover para `src/test` real (F2) | 🔲 |
| Dependências `commons-io` e `pdfbox` (declaradas, nunca importadas) | Remover do `pom.xml` raiz | 🔲 |

---

## Como usar esta matriz

1. Cada onda da **F3** (seção 06 do relatório) fecha um bloco de linhas
   acima — ao homologar uma estratégia, marque a linha como ✅ e registre a
   data/observação.
2. Itens marcados **Corrigir e migrar** exigem uma decisão explícita
   documentada (preservar o bug ou corrigir) antes de fechar a linha — nunca
   silenciosamente.
3. A **seção 9** pode ser executada a qualquer momento, independente das
   ondas, desde que a tag de salvaguarda já exista.
4. Esta matriz é viva — itens descobertos durante a migração (ex.: uso oculto
   de uma classe "morta") entram como nova linha, nunca são ignorados.
