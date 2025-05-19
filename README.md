=== ENGLISH ===
-

Version: 1.0

= DESCRIPTION =

The Java program that performs difficult calculations for interferogram analysis.

= REQUIREMENTS =

1. Java 11 and newer
2. Git system installed on your system (optional, but desirable)

= HOW TO INSTALL =

To install the program correctly follow these steps:

1. Download the entire repository with this command in Terminal/CMD depending on your OS:


    $ git clone https://github.com/ivansed2004/calculator

    (if git system is absent on your system, you can simply download the repository using "Code" -> "Download ZIP")

2. Run the .jar file with the command below:


    $ java -jar calculator-1.0.jar

= YAML SETTINGS =

1. The current config.yaml file (src/main/resources/config.yaml) allows you to customize the program usage:


    pre-calculation-settings:
       default-target-directory: <absolute path>
       runtime-target-directory-selection: <always/never>

   - default-target-directory is specified for the program to use constant target directory path.
   - runtime-target-directory-selection is specified for the program to always/never require the GUI selection of target directory.

= HOW TO USE =

1. At the very beginning the program asks you to choose the target directory where result files will be generated into.

2. The next step is that you should choose the source files for further automated calculations.

3. After the program will generate result files with the specification described below:

= INPUT/OUTPUT SPECIFICATION =

1. A source file represents a file with '.dat' extension, that have an integer as a name (1.dat, 2.dat, etc.) and contains the string with the point coordinates. Tab is used as a separator between numbers. For example:


    1.429155E+3 7.6838744E-01

    1.431084E+3 7.6457572E-01

    1.433013E+3 7.6110500E-01

    1.434941E+3 7.5575310E-01

    1.436870E+3 7.5684434E-01
    ...

2. Result files are generated in a directory that name coincides with corresponding source file. Only one directory to a source file! The directory itself contains:
    
    - hyperbola_analyticalN.txt (analytical expression);
    - hyperbola_discreteN.dat (point coordinates);
    - interferogram_analyticalN.txt (the same);
    - interferogram_discreteN.dat (the same);
    - spectrum_discreteN.dat (the same).

    Where N is a source file name integer.

    The .txt files stores the mathematical expression for a given object which name is specified in the name of the file. The .dat files stores the point coordinates that have been taken by sampling the function graph poins.

3. After a target directory of a given name is generated, it can not be regenerated again. For example, if the '3' directory is generated and if you try to regenerate it from the '3.dat' source file, it remains immutable.

=== РУССКИЙ ===
-

Версия: 1.0

= ОПИСАНИЕ =

Программа Java, которая выполняет сложные вычисления для анализа интерферограмм.

= ТРЕБОВАНИЯ =

1. Java 11 и более новые версии
2. Система Git, установленная в вашей системе (необязательно, но желательно)

= КАК УСТАНОВИТЬ =

Чтобы правильно установить программу, выполните следующие действия:

1. Загрузите весь репозиторий с помощью этой команды в Терминале/CMD в зависимости от вашей ОС:


    $ git clone https://github.com/ivansed2004/calculator
    (если система git отсутствует в вашей системе, вы можете просто загрузить репозиторий с помощью "Код" -> "Загрузить ZIP")

2. Запустите файл .jar с помощью следующей команды:


    $ java -jar calculator-1.0.jar

= НАСТРОЙКИ YAML =

1. Текущий файл config.yaml (src/main/resources/config.yaml) позволяет вам настраивать использование программы:


    pre-calculation-settings:
       default-target-directory: <абсолютный путь>
       runtime-target-directory-selection: <всегда/никогда>

- default-target-directory указывается для программы, чтобы использовать постоянный путь к целевому каталогу.
- runtime-target-directory-selection указывается для программы, чтобы всегда/никогда не требовать выбор целевого каталога через GUI.

= КАК ИСПОЛЬЗОВАТЬ =

1. В самом начале программа просит вас выбрать целевой каталог, в который будут сгенерированы файлы результатов.

2. Следующим шагом будет выбор исходных файлов для дальнейших автоматизированных расчетов.

3. После этого программа сгенерирует файлы результатов со спецификацией, описанной ниже:

= СПЕЦИФИКАЦИЯ ВВОДА/ВЫВОДА =

1. Исходный файл представляет собой файл с расширением '.dat', имеющий целое число в качестве имени (1.dat, 2.dat и т. д.) и содержащий строку с координатами точек. В качестве разделителя между числами используется табуляция. Например:


    1.429155E+3 7.6838744E-01
    
    1.431084E+3 7.6457572E-01
    
    1.433013E+3 7.6110500E-01
    
    1.434941E+3 7.5575310E-01
    
    1.436870E+3 7.5684434E-01
    ...

2. Файлы результатов генерируются в каталоге, имя которого совпадает с именем соответствующего исходного файла. Только один каталог для исходного файла! Сам каталог содержит:

   - hyperbola_analyticalN.txt (аналитическое выражение);
   - hyperbola_discreteN.dat (координаты точек);
   - interferogram_analyticalN.txt (то же самое);
   - interferogram_discreteN.dat (то же самое);
   - spectrum_discreteN.dat (то же самое).

    Где N — целое число имени исходного файла.

    Файлы .txt хранят математическое выражение для заданного объекта, имя которого указано в имени файла. Файлы .dat хранят координаты точек, которые были получены путем выборки точек графика функции.

3. После создания целевого каталога с заданным именем его нельзя повторно сгенерировать. Например, если создается каталог «3», и вы пытаетесь повторно сгенерировать его из исходного файла «3.dat», он остается неизменным.
