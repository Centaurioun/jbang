//usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.openjdk.jmh:jmh-core:1.19 org.openjdk.jmh:jmh-generator-annprocess:1.19
//DEPS com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.9
//DEPS com.fasterxml.jackson.core:jackson-databind:2.2.3
package benchmark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
@Fork(value = 1, warmups = 1)
public class perftest {

    String properties = "#Generated by extension-descriptor\n" +
            "#Sun Feb 09 06:57:07 WET 2020\n" +
            "deployment-artifact=io.quarkus\\:quarkus-vault-deployment\\:1.8.1.Final";

    String yaml = "---\n" +
            "deployment-artifact: \"io.quarkus:quarkus-vault-deployment:1.8.1.Final\"\n" +
            "name: \"Jackson\"\n" +
            "metadata:\n" +
            "  keywords:\n" +
            "  - \"jackson\"\n" +
            "  - \"json\"\n" +
            "  categories:\n" +
            "  - \"serialization\"\n" +
            "  status: \"stable\"\n" +
            "group-id: \"io.quarkus\"\n" +
            "artifact-id: \"quarkus-jackson\"\n" +
            "version: \"1.8.1.Final\"\n" +
            "description: \"Jackson Databind support\"\n" +
            "\n";

    @Benchmark
    public void properties() throws IOException {
        Properties p = new Properties();
        p.load(new StringReader(properties));
        String v = p.getProperty("deployment-artifact");
        if(v==null) throw new IllegalStateException();
    }

    @Benchmark
    public void yaml() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map p = mapper.readValue(yaml, Map.class);
        String v = (String) p.get("deployment-artifact");
        if(v==null) throw new IllegalStateException(p.toString());
    }

    public static void main(String... args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

}


