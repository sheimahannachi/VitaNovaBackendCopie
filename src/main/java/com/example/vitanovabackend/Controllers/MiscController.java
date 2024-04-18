package com.example.vitanovabackend.Controllers;


import lombok.RequiredArgsConstructor;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/misc")
public class MiscController {
    private static final String FTP_DIRECTORY_PATH = "C:\\Users\\hamad\\OneDrive\\Desktop\\FTPFOLDER";




    @CrossOrigin("**")
    @PostMapping("/takePic")
    public static boolean captureAndSaveImage() {
        // Load OpenCV library
        OpenCV.loadLocally();

        // Open the default camera (index 0)
        VideoCapture camera = new VideoCapture(0);

        // Check if the camera is opened successfully
        if (!camera.isOpened()) {
            System.err.println("Error: Unable to open camera!");
            return false;
        }

        // Create a Mat object to store the captured frame
        Mat frame = new Mat();

        // Capture a frame
        if (!camera.read(frame)) {
            System.err.println("Error: Unable to capture frame from camera!");
            return false;
        }

        // Save the captured frame as an image (with a valid file extension)
        String filePath = "C:\\Users\\hamad\\OneDrive\\Desktop\\test.jpg";
        boolean savedSuccessfully = Imgcodecs.imwrite(filePath, frame);
        if (!savedSuccessfully) {
            System.err.println("Error: Unable to save image at " + filePath);
            camera.release();

            return false;
        }

        // Release the camera
        camera.release();

        System.out.println("Image captured and saved successfully at: " + filePath);
        return true;
    }






}

