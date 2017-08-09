package jp.sbkro.benckmark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import jp.sbkro.benchmark.Person;;

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ArrayListBenchmark {

    private static List<Person> looptarget;

    private static final int listsize = 10000;

    @Setup
    public void setup() {
        looptarget = new ArrayList<>();
        for (int i = 0; i < listsize; i++) {
            Person person = new Person();
            person.setNo(i);
            person.setName(String.format("name-%d", i));
            looptarget.add(person);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public void forloop() {
        List<String> names = new ArrayList<>();

        for (int i = 0; i < looptarget.size(); i++) {
            Person person = looptarget.get(i);
            if (person.getNo() % 10 == 0) {
                names.add(person.getName());
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public void whileloop() {
        int i = 0;
        List<String> names = new ArrayList<>();

        do {
            Person person = looptarget.get(i);
            if (person.getNo() % 10 == 0) {
                names.add(person.getName());
            }

            i++;
        } while (i < looptarget.size());
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public void foreach() {
        List<String> names = new ArrayList<>();
        for (Person person : looptarget) {
            if (person.getNo() % 10 == 0) {
                names.add(person.getName());
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    public void lambda() {
        List<String> names = null;
        names = looptarget.stream().filter(i -> (i.getNo() % 10 == 0)).map(j -> j.getName())
                .collect(Collectors.toList());
    }
}
