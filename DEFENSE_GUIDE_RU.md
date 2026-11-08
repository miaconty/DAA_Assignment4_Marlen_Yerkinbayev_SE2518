# DAA Assignment 4: подготовка к защите

**Студент:** Marlen Yerkinbayev, SE2518

## 1. Что делает проект

Программа сравнивает пять алгоритмов. Три считают минимальное количество
вставок, удалений и замен между строками. Два ищут все появления шаблона
в тексте. Потом программа считает операции и время и сохраняет CSV.

## 2. Три версии Edit Distance

- **Recursive**: функция `solve(i,j)` вызывает себя для уменьшенных префиксов.
  Если символы равны, переходим по диагонали, иначе берём минимум из
  удаления, вставки и замены плюс один. Без кэша повторяет работу.
- **Table DP**: `dp[i][j]` хранит ответ для первых `i` и `j` символов.
  Время O(nm), память O(nm). Начальные строка/столбец равны 0,1,2,...
- **Two Rows**: хранит только предыдущую и текущую строки таблицы.
  Время O(nm), память O(min(n,m)), но полную таблицу потом не восстановить.

Термины: *state* = состояние; *transition* = формула перехода;
*base case* = базовый случай; *memoization* = запоминание результатов;
*bottom-up* = решение от маленьких подзадач к большим.

## 3. Поиск строки

- **Naive**: проверяет каждую стартовую позицию, до O(nm).
- **KMP**: заранее строит LPS, затем использует его, чтобы не возвращаться
  назад по тексту. Время O(n+m); массив LPS занимает O(m) памяти.
- **LPS**: длина самого длинного собственного префикса подстроки, который
  одновременно является её суффиксом.
- **Overlap**: `AAAAA` и `AA` дают `[0,1,2,3]`. В KMP после совпадения
  `j = prefix[j - 1]` сохраняет возможное перекрытие.

## 4. Файлы

- `EditDistance.java` и `StringSearch.java`: общие интерфейсы.
- `RecursiveEditDistance.java`, `TableEditDistance.java`,
  `TwoRowsEditDistance.java`, `NaiveSearch.java`, `KMPSearch.java`: алгоритмы.
- `InputGenerator.java`: повторяемые входные строки с фиксированным seed.
- `Experiment.java`: прогрев, 5 замеров, запись в CSV.
- `AlgorithmTest.java`: JUnit 5, эталонные и по 100 случайных тестов.
- `plot_results.py`: построение трёх PNG-графиков из CSV.
- `REPORT.md`: дизайн исследования, ожидания, графики, выводы.

## 5. Возможные вопросы преподавателя

**Почему рекурсия медленная?** Много раз решает одинаковые задачи без
сохранения ответов; сложность растёт экспоненциально.

**Почему DP быстрее?** Каждое состояние вычисляется ровно один раз.

**Почему два ряда дают тот же ответ?** Для новой строки нужны только
текущая строка слева и предыдущая строка сверху/по диагонали.

**Зачем LPS?** При несовпадении можно переиспользовать совпавший префикс.

**Зачем счётчики, если есть миллисекунды?** Время шумит из-за JIT, ОС
и железа. Счётчики показывают фактическое количество работы.

**Почему медиана 5 измерений?** Она менее чувствительна к случайным выбросам.

**Что даёт fixed seed?** Одинаковые входы и счётчики при повторном запуске,
но время в миллисекундах всё равно может немного меняться.

**Какой поиск выбрать для платформы?** KMP, если текст большой и один
шаблон ищется последовательно: его время линейно в худшем случае.

## 6. Короткая речь для защиты (English)

"My project implements three Edit Distance methods and two string-search
methods. Recursive Edit Distance repeats subproblems, while dynamic
programming computes each state once. The two-row version saves memory.
For search, the naive method checks each position, and KMP uses an LPS
array to skip unnecessary comparisons. I tested the algorithms with JUnit,
then ran five timing repetitions per input, recorded actual operation
counters, and visualized the results. The plots compare algorithmic growth
and memory usage. I used fixed random seeds to make the input reproducible."

## 7. Показать на защите

Запусти `mvn test`, затем открой `results/results.csv`, три PNG в
`results/plots/`, отчёт `REPORT.md`. В терминале покажи `git log --oneline`,
`git branch`, `git tag`.
