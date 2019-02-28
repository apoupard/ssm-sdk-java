package io.civis.ssm.sdk.client.fabric;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Objects;

public class InvokeArgs {

    private String function;

    private ArrayList<String> values;

    public InvokeArgs(String function, String... values) {
        this.function = function;
        this.values = Lists.newArrayList(values);
    }

    public String getFunction() {
        return function;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvokeArgs that = (InvokeArgs) o;
        return Objects.equals(function, that.function) &&
                Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(function, values);
    }

    @Override
    public String toString() {
        return "InvokeArgs{" +
                "function='" + function + '\'' +
                ", values=" + values +
                '}';
    }
}
