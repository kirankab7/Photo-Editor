package com.grafocrate.photoeditor

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.grafocrate.photoeditor.databinding.FragmentPhotoEditBinding
//import org.opencv.android.Utils
//import org.opencv.core.CvType
//import org.opencv.core.Mat
//import org.opencv.core.Scalar
//import org.opencv.core.Core

class PhotoEditFragment : Fragment() {

    private var _binding: FragmentPhotoEditBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null
    private val args: PhotoEditFragmentArgs by navArgs()
    private val viewModel: PhotoEditViewModel by viewModels()
    private var currentBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoUri: Uri = Uri.parse(args.photoUri)
        val bitmap = BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(photoUri))
        viewModel.setImageBitmap(bitmap)

        viewModel.imageBitmap.observe(viewLifecycleOwner, Observer { updatedBitmap ->
            binding.imageView.setImageBitmap(updatedBitmap)
        })
//        viewModel = ViewModelProvider(this).get(PhotoEditViewModel::class.java)
//
//        imageUri = arguments?.getParcelable("imageUri")
//        imageUri?.let { uri ->
//            binding.imageView.setImageURI(uri)
//        }

        binding.buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.buttonToggleOptions.setOnClickListener {
            val visibility = if (binding.scrollViewToggle.visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }
            binding.scrollViewToggle.visibility = visibility
        }

        binding.buttonUndo.setOnClickListener { viewModel.undo() }
        binding.buttonRedo.setOnClickListener { viewModel.redo() }
        binding.buttonDelete.setOnClickListener { viewModel.delete() }
        binding.buttonSave.setOnClickListener { viewModel.saveImage() }
        binding.buttonShare.setOnClickListener {
            viewModel.shareImage()
            findNavController().navigate(R.id.action_photoEditFragment_to_saveFragment)
        }

        binding.buttonBasic.setOnClickListener {
            binding.scrollViewBasic.visibility = View.VISIBLE
            binding.scrollViewAdvanced.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
//            binding.seekBarFilter.visibility =View.GONE

            binding.seekBarBrightness.visibility = View.GONE
            binding.seekBarContrast.visibility = View.GONE
            binding.seekBarSaturation.visibility = View.GONE
            binding.seekBarHue.visibility = View.GONE
            binding.seekBarTemperature.visibility = View.GONE
        }

        binding.buttonAdvanced.setOnClickListener {
            binding.scrollViewBasic.visibility = View.GONE
            binding.scrollViewAdvanced.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
//            binding.seekBarFilter.visibility =View.GONE
        }

        binding.buttonFilter.setOnClickListener {
            binding.scrollViewBasic.visibility = View.GONE
            binding.scrollViewAdvanced.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            binding.seekBarBrightness.visibility = View.GONE
            binding.seekBarContrast.visibility = View.GONE
            binding.seekBarSaturation.visibility = View.GONE
            binding.seekBarHue.visibility = View.GONE
            binding.seekBarTemperature.visibility = View.GONE

//            binding.seekBarFilter.visibility =View.VISIBLE
        }
        binding.buttonBrightness.setOnClickListener {
            binding.seekBarBrightness.visibility = View.VISIBLE
            binding.seekBarContrast.visibility = View.GONE
            binding.seekBarSaturation.visibility = View.GONE
            binding.seekBarHue.visibility = View.GONE
            binding.seekBarTemperature.visibility = View.GONE
        }
        binding.buttonContrast.setOnClickListener {
            binding.seekBarBrightness.visibility = View.GONE
            binding.seekBarContrast.visibility = View.VISIBLE
            binding.seekBarSaturation.visibility = View.GONE
            binding.seekBarHue.visibility = View.GONE
            binding.seekBarTemperature.visibility = View.GONE
        }
        binding.buttonSaturation.setOnClickListener {
            binding.seekBarBrightness.visibility = View.GONE
            binding.seekBarContrast.visibility = View.GONE
            binding.seekBarSaturation.visibility = View.VISIBLE
            binding.seekBarHue.visibility = View.GONE
            binding.seekBarTemperature.visibility = View.GONE
        }
        binding.buttonHue.setOnClickListener {
            binding.seekBarBrightness.visibility = View.GONE
            binding.seekBarContrast.visibility = View.GONE
            binding.seekBarSaturation.visibility = View.GONE
            binding.seekBarHue.visibility = View.VISIBLE
            binding.seekBarTemperature.visibility = View.GONE
        }
        binding.buttonTemperature.setOnClickListener {
            binding.seekBarBrightness.visibility = View.GONE
            binding.seekBarContrast.visibility = View.GONE
            binding.seekBarSaturation.visibility = View.GONE
            binding.seekBarHue.visibility = View.GONE
            binding.seekBarTemperature.visibility = View.VISIBLE
        }
        // Setup RecyclerView for filters
//        val filters = (1..80).map { FilterAdapter.Filter("Filter $it", R.drawable.ic_launcher_foreground) }
//        val adapter = FilterAdapter(filters) { filter, progress ->
//            // Handle filter application with progress value
//            viewModel.applyFilter(filter.name, progress)
//        }
//
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation =LinearLayoutManager.HORIZONTAL
        binding.recyclerView.layoutManager =layoutManager
        val adapter = FilterAdapter( )
        binding.recyclerView.adapter = adapter

        binding.buttonCrop.setOnClickListener {
            // Handle crop functionality
            cropImage()
        }

        binding.buttonRotate.setOnClickListener {
            // Handle rotate functionality
            rotateImage(90f)
        }

        binding.buttonTranslate.setOnClickListener {
            // Handle translate functionality
            translateImage(10f, 10f)
        }

        binding.buttonFlip.setOnClickListener {
            // Handle flip functionality
            flipImage()
        }

        binding.buttonZoom.setOnClickListener {
            // Handle zoom functionality
            zoomImage(1.2f)
        }

        // Set up SeekBar for brightness adjustment
        binding.seekBarBrightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                adjustBrightness(progress - 100)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    private fun cropImage() {
        // Add crop functionality
        // You can use a cropping library like uCrop or PhotoCrop
    }

    private fun rotateImage(degrees: Float) {
        currentBitmap?.let {
            val matrix = Matrix()
            matrix.postRotate(degrees)
            val rotatedBitmap = Bitmap.createBitmap(it, 0, 0, it.width, it.height, matrix, true)
            binding.imageView.setImageBitmap(rotatedBitmap)
            currentBitmap = rotatedBitmap
        }
    }

    private fun translateImage(dx: Float, dy: Float) {
        binding.imageView.translationX += dx
        binding.imageView.translationY += dy
    }

    private fun flipImage() {
        currentBitmap?.let {
            val matrix = Matrix()
            matrix.postScale(-1f, 1f, it.width / 2f, it.height / 2f)
            val flippedBitmap = Bitmap.createBitmap(it, 0, 0, it.width, it.height, matrix, true)
            binding.imageView.setImageBitmap(flippedBitmap)
            currentBitmap = flippedBitmap
        }
    }

    private fun zoomImage(scaleFactor: Float) {
        binding.imageView.scaleX *= scaleFactor
        binding.imageView.scaleY *= scaleFactor
    }
    private fun adjustBrightness(brightness: Int) {
        currentBitmap?.let {
            val bmp = it.copy(Bitmap.Config.ARGB_8888, true)
            for (x in 0 until bmp.width) {
                for (y in 0 until bmp.height) {
                    val pixel = bmp.getPixel(x, y)
                    val r = Color.red(pixel) + brightness
                    val g = Color.green(pixel) + brightness
                    val b = Color.blue(pixel) + brightness
                    bmp.setPixel(
                        x, y, Color.rgb(
                            r.coerceIn(0, 255),
                            g.coerceIn(0, 255),
                            b.coerceIn(0, 255)
                        )
                    )
                }
            }
            binding.imageView.setImageBitmap(bmp)
            currentBitmap = bmp
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






