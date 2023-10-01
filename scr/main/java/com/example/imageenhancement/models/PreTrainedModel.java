public class EfficientNetB7 extends PreTrainedModel {

    public EfficientNetB7() throws IOException {
        super("src/main/resources/models/efficientnet-b7/efficientnet_b7.onnx");
    }

    @Override
    public BufferedImage processImage(BufferedImage image) throws IOException {
        // Convert the image to a tensor
        Tensor inputTensor = new Tensor(image);

        // Run the pre-trained model
        List<Tensor> outputTensors = session.run(inputTensor);

        // Convert the output tensor to an image
        BufferedImage outputImage = new BufferedImage(outputTensors.get(0).getDimensions()[1], outputTensors.get(0).getDimensions()[2], BufferedImage.TYPE_INT_RGB);
        outputImage.getRaster().setDataElements(outputTensors.get(0).getFloatData());

        return outputImage;
    }
}
