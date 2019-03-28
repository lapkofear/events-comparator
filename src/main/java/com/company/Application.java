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
        LOG.info("Start comparing files " + Arrays.toString(filenames));
        try (FileReader reader1 = new FileReader(new File(filenames[0])); FileReader reader2 = new FileReader(new File(filenames[1]))) {

            Iterator<String> resultIterator =
                    new OuterJoin().perform(new BufferedReader(reader1).lines().iterator(), new BufferedReader(reader2).lines().iterator());

            File newFile = new File("result_" + System.currentTimeMillis());
            newFile.createNewFile();
            try (FileWriter fileWriter = new FileWriter(newFile)) {
                while (resultIterator.hasNext()) {
                    fileWriter.write(resultIterator.next() + "\n");
                }
            }

            LOG.info("Done in {} ms. Result is written into file: {}", System.currentTimeMillis() - start, newFile.getAbsolutePath());
        }
    }

}
