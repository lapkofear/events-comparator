package com.company;

import com.company.events.OuterJoin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class.getName());

    //TODO: add validation of the file existing and count

    public static void main(String... filenames) throws IOException {
        long start = System.currentTimeMillis();

        Iterator<String> iterator1 = new BufferedReader(new FileReader(new File(filenames[0]))).lines().iterator();
        Iterator<String> iterator2 = new BufferedReader(new FileReader(new File(filenames[1]))).lines().iterator();

        LOG.info("Start comparing files " + Arrays.toString(filenames));

        Iterator<String> resultIterator = new OuterJoin().perform(iterator1, iterator2);

        File newFile = new File("result_" + System.currentTimeMillis());
        newFile.createNewFile();
        FileWriter fileWriter = new FileWriter(newFile);
        while (resultIterator.hasNext()) {
            fileWriter.write(resultIterator.next() + "\n");
        }

        LOG.info("Done in {} ms. Result is written into file: {}", System.currentTimeMillis() - start, newFile.getAbsolutePath());
    }

}
