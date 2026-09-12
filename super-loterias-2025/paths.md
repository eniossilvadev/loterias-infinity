# Índice de classes ricas (geradores ativos por loteria)

> Corrigido em 2026-09-12 (F0 da modernização). Os FQNs abaixo foram
> verificados um a um contra o código-fonte atual — a versão anterior deste
> arquivo usava o prefixo antigo `com.enio.silva.cliente.*`, que não existe
> mais (o pacote raiz real é `br.com.enio.silva.loterias.cliente.*`), e
> continha duas entradas duplicadas/erradas (removidas, com nota abaixo).
>
> Este arquivo é o ponto de partida da **matriz de rastreabilidade** da
> modernização — cada classe aqui precisa de destino explícito (migrar /
> absorver / arquivar) antes do legado poder ser desligado.

## Bingo da Sorte

```java
br.com.enio.silva.loterias.cliente.geradores.SuperBingoDaSorte2024
br.com.enio.silva.loterias.cliente.geradores.SuperBingoDaSorte2024Iterate
```

## Lotofácil

```java
br.com.enio.silva.loterias.cliente.geradores.lotofacil.LotofacilFullUIltimoConcursoQuadrante
br.com.enio.silva.loterias.cliente.geradores.lotofacil.GerarLotofacilRandomSoInclPremiadosFull
br.com.enio.silva.loterias.cliente.geradores.lotofacil.Lotofacil2024InvertFromList
br.com.enio.silva.loterias.cliente.geradores.lotofacil.Lotofacil2024InvertFullList
br.com.enio.silva.loterias.cliente.geradores.lotofacil.SimpleLotofacilFullNovoLoopInverteLoopLastQuadranteNew
br.com.enio.silva.loterias.cliente.geradores.lotofacil.LotofacilNovoLoopInverteLoopLastQuadranteNew
br.com.enio.silva.loterias.cliente.geradores.lotofacil.LotofacilNovoLoopInverteLoopLastQuadrante
br.com.enio.silva.loterias.cliente.geradores.lotofacil.LotofacilNovoLoopInverteLoopLast
br.com.enio.silva.loterias.cliente.geradores.lotofacil.LotofacilUIltimoConcursoQuadrante
br.com.enio.silva.loterias.cliente.lotofacil.current.GerarLotofacilNovoLoopInverteLoopLastMaisDe15

# Flagship atual (não estava no índice original — é o gerador mais novo/refatorado)
br.com.enio.silva.loterias.cliente.geradores.lotofacil.SuperLotofacil2026

# WIP não commitado na F0 (arquivo novo, ainda em desenvolvimento)
br.com.enio.silva.loterias.cliente.geradores.lotofacil.LotofacilInverte
```

⚠️ **Mal alocada** — é um gerador de Lotofácil, mas vive no pacote `cliente.lotomania`
por engano (achado confirmado no levantamento). Mover para
`cliente.geradores.lotofacil` é uma correção candidata da F2/F3, não da F0:

```java
br.com.enio.silva.loterias.cliente.lotomania.LotofacilFullNovoLoopInverteLoopLastQuadranteNew
```

## Mega-Sena

```java
br.com.enio.silva.loterias.cliente.geradores.megasena.MegaFullLoopExcluirIncluirTopezaMaisDe6MaisMenos

# Flagships atuais (não estavam no índice original)
br.com.enio.silva.loterias.cliente.geradores.megasena.SuperMega2025
br.com.enio.silva.loterias.cliente.geradores.megasena.SuperMega2026
```

> Nota: o índice anterior também listava
> `com.enio.silva.cliente.geradores.megasena.GerarJogosMegaLoopExcluirIncluirTopezaMaisDe6MaisMenos`
> como entrada separada. Essa classe **não existe** — era o mesmo
> `MegaFullLoopExcluirIncluirTopezaMaisDe6MaisMenos` listado com nome errado.
> Entrada removida (duplicata).

## Quina

```java
br.com.enio.silva.loterias.cliente.geradores.quina.QuinaQuadranteBaseN
br.com.enio.silva.loterias.cliente.geradores.quina.QuinaComBaseFixa
br.com.enio.silva.loterias.cliente.geradores.quina.QuinaComBaseMovel
br.com.enio.silva.loterias.cliente.geradores.quina.QuinaLoopExcluirIncluirPreJogo

# WIP não commitado na F0 (arquivo novo, ainda em desenvolvimento)
br.com.enio.silva.loterias.cliente.geradores.quina.QuinaQuadranteBase2025N
```

> Nota: o índice anterior também listava
> `com.enio.silva.cliente.geradores.quina.QuinaLoopExcluirIncluirPreJogoFromList`.
> Essa classe **não existe** — entrada removida (duplicata/erro de digitação
> de `QuinaLoopExcluirIncluirPreJogo`).

## Lotomania

```java
br.com.enio.silva.loterias.cliente.lotomania.LotomaniaFullPremiado
```

## Dupla-Sena

```java
br.com.enio.silva.loterias.cliente.geradores.duplasena.GerarJogosDuplaSena2024
```

## Timemania

> Ausente do índice original — único gerador ativo da loteria, cobertura
> mínima (ver relatório de levantamento, seção 2.3):

```java
br.com.enio.silva.loterias.cliente.timemania.GerarJogosTimemaniaLoopExcluirIncluir10
```

## Misc (utilitários de suporte usados pelos geradores acima)

```java
br.com.enio.silva.loterias.cliente.mix.ClienteRemoveDuplicados
br.com.enio.silva.loterias.cliente.mix.LimpaLoto
br.com.enio.silva.loterias.cliente.geradores.lotofacil.ShiftAndRotateLotofacil
br.com.enio.silva.loterias.cliente.mix.SplitAndJoin
br.com.enio.silva.loterias.cliente.geradores.lotofacil.GetXNumbersUtil
```

---

Ver também: [Operação Legadão — relatório de levantamento e plano de
modernização](https://claude.ai/code/artifact/c885ae6d-c796-4786-b6fb-2c10e8221e46)
e a matriz de rastreabilidade em `MATRIZ-RASTREABILIDADE.md` (raiz do
projeto).
