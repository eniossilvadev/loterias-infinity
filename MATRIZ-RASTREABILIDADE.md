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

Legenda de status: 🔲 Pendente · 🟡 Em andamento · 🗄️ Em quarentena (`_to_delete/`) · ✅ Concluído

---

## 1. Estratégias de geração — Lotofácil

| Classe / FQN | Loteria | Destino | Status | Notas |
|---|---|---|---|---|
| `cliente.geradores.lotofacil.SuperLotofacil2026` | Lotofácil | Migrar (onda 1) | ✅ | `estrategias/lotofacil.py::super_lotofacil_2026` — **achado crítico:** como está escrito hoje, este gerador NUNCA produz jogo (pool gerado com 15 dezenas, filtro exige 16). Corrigido: pool gerado direto em 16, com fidelidade total ao resto do pipeline (9–12 vs. último sorteio, janela 150–900, elemento menos frequente excluído). Comportamento comprovado por teste (`test_produz_jogos_de_verdade`) |
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
| `cliente.geradores.megasena.SuperMega2026` | Migrar (onda 1) | ✅ | `estrategias/megasena.py::super_mega_2026` — bolão de 12 dezenas. Corrigido: `nrosJogos` respeitado de verdade; ordem de cálculo incluir(menos frequentes)/excluir(mais frequentes) confirmada na fonte (a ordem inverte tudo se trocada) |
| `cliente.geradores.megasena.SuperMega2025` | Migrar (onda 1) | ✅ | `estrategias/megasena.py::super_mega_2025` — 6 dezenas. Estruturalmente quase idêntico ao 2026 (mesma lógica incluir/excluir/"lasts"), difere no conjunto/ordem de filtros (sem checagem de superconjunto) e na tabela de pontos — lógica comum compartilhada via `_gerar_portfolio` para não duplicar |
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
| `cliente.geradores.quina.QuinaQuadranteBaseN` | Migrar (onda 1) | ✅ | `estrategias/quina.py::quina_quadrante_base_n` — janela cíclica de pontuação (0,50,0,100,...) confirmada na fonte; 25% de chance de inverter a ordem antes de cortar, preservado fielmente |
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
| `cliente.lotomania.LotomaniaFullPremiado` | Migrar (onda 1) | ✅ | `estrategias/lotomania.py::lotomania_full_premiado` — pontua o "espelho" (complemento 0-99) e soma, cada jogo aceito produz 2 linhas (jogo+espelho). Corrigida a inconsistência de convenção numérica do legado (`fixInput` convertia "0"→"100" só em parte dos dados, nunca nos candidatos — aqui tudo é 0-99 de ponta a ponta) |
| `cliente.lotomania.GerarLotomaniaMain` (+ `New`/`NewXTimes`/`Random`/`RandomXTimes`/`NewPremiados`) | Absorver | 🔲 | 6 variações incrementais → 1 componente parametrizado |
| `cliente.frequencia.CalcularFrequenciaLotomania` | Migrar (onda 3) | 🔲 | Top/bottom por frequência — paths pessoais em `D:\Meus Documentos\` |
| `cliente.temp.GerarEspelhosLotomanis`, `GerarJogoLotomaniaComplemento` | Migrar (onda 3) | 🔲 | Espelho/complemento 0–99 |
| `commons-loterias.controller.LotomaniaRN.aplicarFiltro/aplicarFiltro2` | Migrar (F2) | 🔲 | Filtros de repetição (7–10) e par/ímpar (≤2) — únicos filtros de negócio reais fora do pacote `filtro/` |

## 5. Estratégias de geração — Dupla Sena, Timemania, Bingo da Sorte

| Classe / FQN | Loteria | Destino | Status | Notas |
|---|---|---|---|---|
| `cliente.geradores.duplasena.GerarJogosDuplaSena2024` | Dupla Sena | Migrar (onda 1) | ✅ | `estrategias/dupla_sena.py::gerar_jogos_dupla_sena_2024` — confirmado na fonte: sem filtro de qualidade de jogo (só duplicata exata), e o cálculo de frequência (`mapAtuais`) é código morto — nunca usado para escolher `elements` (que é aleatório uniforme). Simplificação documentada: tabela de pontos fixa por execução (o legado cicla entre 4 configs/tabelas não levantadas ainda) |
| `gerador.GerarJogosDuplaLoop` / `...EqualizaMaisMenos` | Dupla Sena | Absorver | 🔲 | |
| `cliente.timemania.GerarJogosTimemaniaLoopExcluirIncluir10` | Timemania | Migrar (onda 3) | 🔲 | Único gerador — depende de F1 (fechar gap de histórico) |
| `cliente.geradores.SuperBingoDaSorte2024` | Bingo da Sorte | Migrar (onda 1) | ✅ | `estrategias/bingo_da_sorte.py::super_bingo_da_sorte_2024` — confirmado na fonte: `QuinaConfig10.getPontuador()` sorteia 50/50 entre duas tabelas A CADA CANDIDATO (não por rodada) — comportamento incomum mas real, replicado fielmente. Teto de tentativas GLOBAL (não por rodada), único flagship com essa característica |
| `cliente.geradores.SuperBingoDaSorte2024Iterate` | Bingo da Sorte | Absorver | 🔲 | Duplicata (`QUANTIDADE_DE_JOGOS=1`) do item acima |
| `cliente.geradores.SuperBingoDaSorte` | Bingo da Sorte | Arquivar | 🔲 | Superada pela versão 2024 |
| `cliente.bingodasorte.gerador.GeradorGrupoBingoDaSorte` (+ variantes Novo/NovoComLista/Quadrante) | Bingo da Sorte | Absorver | 🔲 | Motor de 6 grupos temáticos — consolidar variantes num único motor parametrizado |
| `cliente.bingodasorte.newp.Novo01..Novo08` | Bingo da Sorte | Arquivar | 🔲 | Abandonadas (bases de 2022/2023) — lógica já coberta pelo motor de grupos |
| `cliente.geradores.quina.NovoBingoDaSorte(_1)`, `BingoDaSorteQuadranteBaseN(FromList)` | Bingo da Sorte | Absorver | 🔲 | Ver linha da Quina acima |
| `cliente.bingodasorte.gen.*`, `cliente.bingodasorte.ModeloBingoDaSorte` | Bingo da Sorte | Arquivar | 🔲 | Modelagem paralela/duplicada, classe de 7 linhas |
| Cobertura de dados (Dupla Sena / Timemania / Bingo) | — | **Bloqueador de F1** | 🔲 | Nenhuma base histórica moderna cobre essas 3 — pré-requisito antes de migrar os geradores |

## 6. Peças transversais (filtros, pontuadores, pós-processadores)

> **Atualizado em 2026-09-12 (F2, primeira rodada):** filtros, pontuadores,
> quadrantes/rotação e pool aleatório migrados para
> `loterias-pro/loterias_pro/nucleo/` (repositório novo, ver
> [README](../loterias-pro/README.md)), com 85 testes cobrindo os 3
> módulos. Levantamento feito lendo o código Java linha a linha (não
> resumo) — ver achados abaixo.
>
> **Achado crítico da F2:** `ConfigJogosComum.nrosJogos` tem um setter que
> **nunca é chamado em lugar nenhum do projeto**. Consequência: em todos os
> 7 geradores flagship lidos (`SuperLotofacil2026`, `SuperMega2025/2026`,
> `QuinaQuadranteBaseN`, `SuperBingoDaSorte2024`, `GerarJogosDuplaSena2024`,
> `LotomaniaFullPremiado`), o corte "top N jogos" (`subList(0,
> config.getNrosJogos())`) sempre corta **top-1**, nunca o N configurado —
> as constantes `QUANTIDADE_DE_JOGOS` controlam apenas o número de rodadas
> do loop externo, cada uma produzindo exatamente 1 jogo. Adicionalmente,
> `SuperLotofacil2026` (o flagship da Lotofácil) **nunca produz jogo
> algum, como está escrito hoje** — os candidatos são gerados com 15
> dezenas mas o filtro seguinte exige 16, descartando 100% dos candidatos
> sempre. **Decisão registrada:** implementar o comportamento pretendido
> (respeitar `nrosJogos` de verdade; corrigir o tamanho do pool da
> Lotofácil) em vez de replicar os bugs — cada correção documentada no
> código com referência ao comportamento antigo.
>
> **Atualizado em 2026-09-17 (F3, onda 1 completa):** os 7 geradores
> flagship foram portados para `loterias-pro/loterias_pro/estrategias/`,
> cada um lido linha a linha na fonte antes de portar — ver as linhas
> correspondentes nas seções 1–5 acima. `nrosJogos` agora é
> `jogos_por_rodada`, um parâmetro real, em todos os 7; o pool da Lotofácil
> é gerado direto no tamanho final (16), corrigindo o bug que fazia
> `SuperLotofacil2026` nunca produzir jogo (comprovado por teste). Outro
> achado real (não estava no levantamento original): `FiltroRemoverIntersecao`
> tem o guard de tamanho invertido no Java, tornando-o um no-op exatamente
> no caso de uso mais comum (candidato maior que o histórico) — corrigido
> em `nucleo/filtros.py::remover_intersecao`. 199 testes no total.

| Componente | Destino | Status | Notas |
|---|---|---|---|
| 23 classes em `filtro/*` | Migrar (F2) | ✅ | `nucleo/filtros.py` — ~15 filtros distintos (2 pares eram equivalentes para parâmetros reais, colapsados). 3 bugs corrigidos: guard de tamanho invertido em `FiltroRemoverIntersecao` (era no-op no uso real mais comum); `FiltroMinimoColunas` com crescimento exponencial em vez de incremento; `FiltroMaxLista` rejeitando sempre quando `max<=0`. Estado `static` de `FiltroSubstituirUltimoSorteio` virou parâmetro de função (sem estado global) |
| ~45 classes de pontuador (`pontuador/*`, `config/pontuador/*`, `duplasena/*`, `pontuador/lotomania/*`) | Migrar (F2) | 🟡 | `nucleo/pontuador.py` — motor genérico + catálogo com 10 tabelas portadas (Lotofácil ×5, Mega ×2, Lotomania ×5/8 já cobertas — as tabelas específicas de Quina/Dupla Sena/+Milionária/Timemania ficam para quando os respectivos flagships forem portados) |
| `ShiftBy` | Migrar (F2) | ✅ | `nucleo/pool.py::aplicar_shift` — hardcode do legado (`shift=0` virava `26` fixo) corrigido com aritmética modular própria |
| `ShiftByNew` | Arquivar | 🗄️ | Duplicata byte a byte de `ShiftBy` (movido para quarentena) |
| `PositionalReplacement` | Migrar (F2) | ✅ | `nucleo/pool.py::aplicar_substituicao_posicional` |
| `PositionalReplacementNew` | Arquivar | 🗄️ | Duplicata byte a byte (movido para quarentena) |
| `Quadrantes*` (Lotofácil/Mega/Quina/DuplaSena/Lotomania) + `Rotacao` | Migrar (F2) | ✅ | `nucleo/quadrantes.py` — quadrantes fixos verificados linha a linha contra o Java (Dupla Sena confirmadamente irregular: 13/12/13/12); Lotomania calculada (fórmula par/ímpar de terminação). `Rotacao.getList` portado com simplificação de eficiência sem mudar distribuição (`rng.choice` em vez de embaralhar a lista inteira) |
| `gerador.Base`, `GerarJogosBase`, `GerarJogosDuplaBase` | Migrar (F2/F3) | ✅ | Absorvidas: `nucleo/selecao.py` (filtrar+pontuar+ordenar+cortar, `mais_frequentes`/`menos_frequentes`) + a orquestração específica de cada `estrategias/*.py`. Não viraram uma superclasse — cada flagship compõe as mesmas peças do núcleo à sua própria maneira |
| `conferator/*` (11 classes) | Migrar (F4) | ✅ | `nucleo/conferencia.py` — catálogo `PREMIACOES` (Lotofácil, Mega-Sena, Dupla Sena — as 3 únicas com conferidor no legado; Quina/Lotomania/Timemania/Bingo nunca tiveram) substitui as 11 classes |
| `conferencia/*` (4 classes) | Arquivar | 🔲 | Conferência v1, superada por `conferator/*` (já migrado) |
| `commons-loterias.controller.ConferirRN` | Corrigir e migrar (F4) | ✅ | `nucleo/conferencia.py::conferir_concurso/conferir_intervalo` — corrigido: levanta `ValueError` claro em vez de devolver `null`/lista com buraco |
| `commons-loterias.controller.Estatisticas` | Corrigir e migrar (F4) | ✅ | `nucleo/estatistica.py::repeticao_entre_consecutivos` — corrigido: `Counter` em vez de array de tamanho fixo, não pode estourar. `calcularEstatisticasNumerosPorPosicao` (por posição) **não portado**: exige a ordem real de sorteio, que o formato canônico deste projeto não guarda (dezenas sempre ordenadas) — documentado como pendência, não omissão |
| `lotomania.ContaAtrasosResultados` | Corrigir e migrar (F4) | ✅ | `nucleo/estatistica.py::atrasos` — **achado real:** era singleton com estado mutável compartilhado entre chamadas (corrompia resultados de históricos diferentes); aqui é função pura |
| Backtest generalizado (visão do relatório original) | Migrar (F4) | ✅ | `nucleo/backtest.py` — harness walk-forward genérico, funciona com qualquer estratégia via injeção de função `gerar`; não é porta de código Java (não existia no legado) |
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
| `commons-loterias.util.ListUtil` (74 imports) | Corrigir e migrar (F2) | ✅ | `nucleo/pool.py::completar_aleatorio` — **bug corrigido:** `incluir` agora funciona de verdade (o legado recebia o parâmetro e nunca o usava) |
| `commons-loterias.util.ListUtil2` | Arquivar | 🗄️ | Versão antiga morta, contém loop vazio e possível loop infinito (movido para quarentena) |
| `commons-core util.ListaUtils` | Absorver | 🔲 | `iterateStream` duplicado com `ListUtil` |
| `commons-loterias.util.LotoUtil` | Arquivar | 🗄️ | Código morto — `maiorSequencia` retorna `null` (movido para quarentena) |
| `commons-loterias.util.MathUtil` | Migrar (F2) | 🔲 | Paridade — reescrever em Stream Java 8+ no lugar de commons-collections 3.x |
| `commons-loterias.util.ImprimirUtil` | Arquivar/Reescrever | 🔲 | Estado estático mutável — vira formatador puro |
| `commons-core util.ZipUtil` | Migrar (F1, se necessário) | 🔲 | Só relevante se algum fluxo de ZIP sobreviver; provavelmente obsoleto após API |
| `commons-core properties.PropertiesUtil` + `download.PropertiesUtil` | Arquivar | 🗄️ | Carregam `config.properties` inexistente — substituídos pelo catálogo de parâmetros declarativo (movido para quarentena) |
| `super-loterias-2025 config.ListOfListComparator` + `ListOfListComparator2` | Absorver | 🔲 | Par duplicado real (não código morto): `ListOfListComparator` é usado por ~30 classes incl. os flagships; `ListOfListComparator2` só por `diversos/LotoUtils.java`. Unificar numa única implementação na F2 |

## 8. Mappers e parsing HTML (candidatos fortes a arquivamento total)

| Componente | Destino | Status | Notas |
|---|---|---|---|
| `model.mapper.GenericMapper` | Arquivar | 🔲 | Parsing HTML por offsets mágicos, sem teste; grava debug em `E:\loterias\retorno.txt`. **Substituído pela ingestão via API (F1)** |
| `model.mapper.*Mapper` (Lotofácil, Mega, Quina, Timemania, Lotomania, DuplaSena) | Arquivar | 🔲 | Todos dependem do `GenericMapper` acima |
| `commons-loterias.controller.DuplaSenaRN` / `RNS1` / `RNS2` | Corrigir e arquivar | 🔲 | Bug confirmado: usa `LotofacilMapper` por engano; `S1`/`S2` retornam o mesmo resultado — registrar antes de descartar (pode ter mascarado comportamento em produção) |
| `controller.LotofacilRN` / `TimemaniaRN` | Arquivar (F1, junto com o parsing HTML) | 🔲 | Ainda referenciadas pelo pipeline atual — só arquivam quando a ingestão via API substituir o parsing HTML |
| `controller.MegaSenalRN` / `QuinaRN` | Arquivar | 🗄️ | Já sem nenhuma referência hoje — movidas para quarentena |
| `controller.CopyOfLotofacilRN` | Arquivar | 🗄️ | Zero referências, `aplicarFiltro2` com corpo vazio (movido para quarentena) |
| `util.ZipUtil` (parsing) + `.zip` da Caixa | Arquivar | 🔲 | Fluxo de download manual + unzip morre com a API |

## 9. Limpeza garantida (zero referências — ver seção 07 do relatório)

> **Atualizado em 2026-09-12:** todos os itens confirmados foram movidos
> para `_to_delete/` (pasta local, ignorada pelo Git — ver
> `_to_delete/README.md`) em vez de apagados diretamente. Ficam em
> quarentena até o expurgo definitivo na F6. O histórico do Git já preserva
> o conteúdo original de qualquer forma.

| Item | Ação | Status |
|---|---|---|
| Módulo `commons` (vazio, só `pom.xml`) | Movido para quarentena; removido de `<modules>` do pom raiz | 🗄️ |
| `commons-core` download/{Combination, DesdobramentoUtil, PropertiesUtil} | Movido para quarentena | 🗄️ |
| `commons-core` math/{Combination, DesdobramentoUtil}, properties/PropertiesUtil | Movido para quarentena | 🗄️ |
| `commons-loterias`: `CopyOfLotofacilRN`, `MegaSenalRN`, `QuinaRN`, `ListUtil2`, `LotoUtil` | Movido para quarentena | 🗄️ |
| `super-loterias-2025`: `ShiftByNew`, `PositionalReplacementNew`, `Constantes` (enum vazio), `GeradorBingoDaSorteIf` (interface vazia) | Movido para quarentena | 🗄️ |
| `.class` órfãos em `src/main/java/.../money/lotomania/` | Movido para quarentena | 🗄️ |
| `src/tmp.xml` (0 bytes), `src/main/resources/temp.json` (nunca lido) | Movido para quarentena | 🗄️ |
| Pasta `testes/` (não compila como teste JUnit) | Movido para quarentena (decisão de recriar em `src/test` real fica para F2) | 🗄️ |
| Dependências `commons-io` e `pdfbox` (declaradas, nunca importadas) | Removidas do `dependencyManagement` do `pom.xml` raiz | ✅ |
| Dependência `e2s-commons` em `commons-loterias/pom.xml` | Removida (apontava para o módulo `commons`, agora em quarentena) | ✅ |

### Correção: item que **não** era código morto

| Item | Correção | Novo destino |
|---|---|---|
| `super-loterias-2025.config.ListOfListComparator2` | Verificação encontrou uso real em `diversos/LotoUtils.java` (`theList.sort(new ListOfListComparator2())`). **Não foi movido.** | Ver linha correspondente na seção 7 — é a segunda metade de um par duplicado com `ListOfListComparator` (usado por ~30 classes, incl. `SuperLotofacil2026` e `SuperMega2025/2026`); destino correto é **Absorver** na F2, não arquivar |

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
