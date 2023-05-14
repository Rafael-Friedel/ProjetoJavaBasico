import builders.StudentsBuilder;
import entities.Studant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;


public class Main {
    // 1.
    public static void listStudentsPassed(String[] args) {
        var allStudents = StudentsBuilder.getAllStudents();
        List<Studant> passedStudents = allStudents.stream()
                .filter(student -> (student.getTestOne() + student.getTestTwo() + student.getTestThree()) / 3.0 >= 7.0)
                .collect(Collectors.toList());

        System.out.println("Alunos que passaram:");
        for (Studant student : passedStudents) {
            float average = (student.getTestOne() + student.getTestTwo() + student.getTestThree()) / 3.0f;
            System.out.println(student.getCode() + " - " + student.getName() + " : Média = " + average);
        }
    }

    // 2.
    public static void listStudentsNotPassed(String[] args) {
        var allStudents = StudentsBuilder.getAllStudents();
        List<Studant> failedStudents = allStudents.stream()
                .filter(student -> (student.getTestOne() + student.getTestTwo() + student.getTestThree()) / 3.0 < 7.0)
                .collect(Collectors.toList());
        System.out.println("Alunos que não passaram:");
        for (Studant student : failedStudents) {
            float average = (student.getTestOne() + student.getTestTwo() + student.getTestThree()) / 3.0f;
            System.out.println(student.getCode() + " - " + student.getName() + " : Média = " + average + " (Faltou = " + (7 - average) + ")");
        }
    }

    // 3.
    public static void findStudentsWithMaximumGrade(String[] args) {
        var allStudents = StudentsBuilder.getAllStudents();
        System.out.println("Alunos com nota máxima:");
        for (Studant student : allStudents) {
            if (student.getTestOne() == 10.0f || student.getTestTwo() == 10.0f || student.getTestThree() == 10.0f) {
                System.out.println(student.getCode() + " - " + student.getName());
            }
        }
    }

    // 4.
    public static void findStudentWithMinimumGrade(String[] args) {
        var allStudents = StudentsBuilder.getAllStudents();
        var minGrade = allStudents.stream()
                .map(student -> (student.getTestOne() + student.getTestTwo() + student.getTestThree()) / 3.0f)
                .min(Float::compare)                    .orElse(null);

        System.out.println("Alunos com a nota mínima (" + minGrade + "):");
        allStudents.stream()
                .filter(student -> (student.getTestOne() + student.getTestTwo() + student.getTestThree()) / 3.0f == minGrade)
                .forEach(student -> System.out.println(student.getCode() + " - " + student.getName() + " : Nota = " + minGrade));
    }

    // 5.
    public static void listThreeTopGrades(String[] args) {
        List<Studant> students = StudentsBuilder.getAllStudents();

        Map<Float, List<String>> scoreMap = new HashMap<>();
        for (Studant student : students) {
            scoreMap.computeIfAbsent(student.getTestOne(), k -> new ArrayList<>()).add(student.getName());
            scoreMap.computeIfAbsent(student.getTestTwo(), k -> new ArrayList<>()).add(student.getName());
            scoreMap.computeIfAbsent(student.getTestThree(), k -> new ArrayList<>()).add(student.getName());
        }
    
        List<Float> orderedScores = scoreMap.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        int position = 1;
        int previousPosition = 0;
        float previousScore = -1;
        System.out.println("Top 3 maiores notas!");
        for (Float score : orderedScores) {
            List<String> studentsWithScore = scoreMap.get(score);
            if (position > 3) {
                break;
            }
            if (score != previousScore) {
                position = previousPosition + 1;
            }
            if (studentsWithScore.size() == 1) {
                System.out.printf("%dº %s = nota %.1f\n", position++, studentsWithScore.get(0), score);
            } else {
                System.out.printf("%dº ", position);
                for (int i = 0; i < studentsWithScore.size(); i++) {
                    System.out.printf("%s = nota %.1f", studentsWithScore.get(i), score);
                    if (i < studentsWithScore.size() - 1) {
                        System.out.print(" - ");
                    }
                }
                System.out.println();
                position += studentsWithScore.size() - 1;
                previousPosition = position - 1;
            }
            previousScore = score;
        }
    }

    // 6.
    public static void listThreeWorstGrades(String[] args) {
        List<Studant> students = StudentsBuilder.getAllStudents();

        Map<Float, List<String>> scoreMap = new HashMap<>();
        for (Studant student : students) {
            scoreMap.computeIfAbsent(student.getTestOne(), k -> new ArrayList<>()).add(student.getName());
            scoreMap.computeIfAbsent(student.getTestTwo(), k -> new ArrayList<>()).add(student.getName());
            scoreMap.computeIfAbsent(student.getTestThree(), k -> new ArrayList<>()).add(student.getName());
        }
    
        List<Float> orderedScores = scoreMap.keySet().stream().sorted().collect(Collectors.toList());
        int position = 1;
        int previousPosition = 0;
        float previousScore = -1;
        System.out.println("Top 3 menores notas!");
        for (Float score : orderedScores) {
            List<String> studentsWithScore = scoreMap.get(score);
            if (position > 3) {
                break;
            }
            if (score == previousScore) {
                position = previousPosition + 1;
            }
            if (studentsWithScore.size() == 1) {
                System.out.printf("%dº %s = nota %.1f\n", position++, studentsWithScore.get(0), score);
            } else {
                System.out.printf("%dº ", position);
                for (int i = 0; i < studentsWithScore.size(); i++) {
                    System.out.printf("%s = nota %.1f", studentsWithScore.get(i), score);
                    if (i < studentsWithScore.size() - 1) {
                        System.out.print(" - ");
                    }
                }
                System.out.println();
                position += studentsWithScore.size() - 1;
                previousPosition = position - 1;
            }
            previousScore = score;
        }
    }
    

    // 7. 
    public static void listStudentsOrderedByGrade(String[] args) {
        var allStudents = StudentsBuilder.getAllStudents();
        List<Studant> sortedStudents = allStudents.stream()
                .sorted((s1, s2) -> Float.compare(s2.getTestOne() + s2.getTestTwo() + s2.getTestThree(), s1.getTestOne() + s1.getTestTwo() + s1.getTestThree()))
                .collect(Collectors.toList());
        System.out.println("Alunos ordenados por nota:");
    
        float previousGrade = -1;
        int position = 0;
        for (Studant student : sortedStudents) {
            float average = (student.getTestOne() + student.getTestTwo() + student.getTestThree()) / 3.0f;
            if (average != previousGrade) {
                position++;
            }
            System.out.println(position + "º - " + student.getCode() + " - " + student.getName() + " : Média = " + average);
            previousGrade = average;
        }
    }

    public static void main(String[] args) {
        listStudentsPassed(args);
        listStudentsNotPassed(args);
        findStudentsWithMaximumGrade(args);
        findStudentWithMinimumGrade(args);
        listThreeTopGrades(args);
        listThreeWorstGrades(args);
        listStudentsOrderedByGrade(args);
    }
}
        // Agora vamos as atividades
        /*
        1. Recupere da lista os alunos que passaram de ano (nota minima 7.0).
            - Exiba os dados nesse formato: <código> - <nome> : Média = <nota>
        2. Recupere da lista os alunos que não passaram de ano.
            - Exiba os dados nesse formato: <código> - <nome> : Média = <media> (Faltou = <nota_faltante>)
        3. Traga os alunos que tiraram a nota máxima (nota 10).
            - Exiba os dados nesse formato: <código> - <nome>

        4. Traga o aluno que tirou a menor nota, em caso de notas iguais, traga ambos os alunos.
            - Exiba os dados nesse formato: <código> - <nome> : Nota = <nota>
        5. Faça uma lista com top 3 notas de alunos. Em caso de notas iguais coloque todos na mesma posição.
            - Ex:
                1º - Fulano : Nota = 10.0;
                   - Beltrano : Nota = 10.0;
                2º - Joãozinho : Nota = 9.0;
                3º - Mariazinha : Nota = 8.9;
            - Exiba os dados nesse formato: <posicao> - <nome> : Nota = <nota>
        6. Faça uma lista com as 3 menores notas de alunos. Em caso de notas iguais coloque todos na mesma posição. Exemplo igual a anterior
            - Exiba os dados nesse formato: <posicao> - <nome> : Nota = <nota>
        7. Monte a média de todos os alunos e exiba em tela ordenando da maior para a menor nota.
            - Exiba os dados nesse formato: <posicao> - <código> - <nome> : Média = <nota>

         */



