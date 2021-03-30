package no.dataingnioer.yamo.demo.utils;

import java.io.PrintStream;
import java.util.Iterator;

import no.dataingnioer.yamo.demo.model.Employee;

/**
 *
 */
public class PrintUtils {

    /**
     *
     * @param iterator
     * @param out
     */
    public static void printEmployeeList(Iterator<Employee> iterator, PrintStream out) {

        while (iterator.hasNext()) {

            Employee employee = iterator.next();
            out.println( employee );
        }

        out.println("-------------------------------------------------------");
    }

}
