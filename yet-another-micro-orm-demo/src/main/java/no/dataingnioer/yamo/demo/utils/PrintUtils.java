package no.dataingnioer.yamo.demo.utils;

import java.io.PrintStream;
import java.util.Iterator;

import no.dataingnioer.yamo.demo.model.Employee;

public class PrintUtils {

    public static void print(Iterator<Employee> iterator, PrintStream out) {

        while (iterator.hasNext()) {

            Employee employee = iterator.next();
            out.println( employee );
        }

        out.println("-------------------------------------------------------");
    }

}
