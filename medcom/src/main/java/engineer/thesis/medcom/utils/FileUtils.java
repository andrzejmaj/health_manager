package engineer.thesis.medcom.utils;

import engineer.thesis.medcom.dicom.repository.exceptions.ArchiveIOException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
public class FileUtils {

    public static List<File> deepFileSearch(File directory, Predicate<File> filter) {
        File[] contents = Optional.ofNullable(directory.listFiles())
                .orElseThrow(() ->
                        new ArchiveIOException(String.format("IO exception occurred while walking archive: %s", directory.getPath())));

        List<File> foundFiles = new ArrayList<>();

        for (final File entry : contents) {
            if (entry.isFile() && filter.test(entry)) {
                foundFiles.add(entry);
            } else if (entry.isDirectory()) {
                foundFiles.addAll(deepFileSearch(entry, filter));
            }
        }

        return foundFiles;
    }

    public static List<File> shallowDirectorySearch(File directory) {
        File[] contents = Optional.ofNullable(directory.listFiles())
                .orElseThrow(() ->
                        new ArchiveIOException(String.format("IO exception occurred while walking archive: %s", directory.getPath())));

        return Arrays.stream(contents)
                .filter(File::isDirectory)
                .collect(Collectors.toList());
    }

}
