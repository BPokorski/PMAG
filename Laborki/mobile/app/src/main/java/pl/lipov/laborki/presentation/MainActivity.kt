package pl.lipov.laborki.presentation

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        info_text.setOnTouchListener(viewModel.getOnTouchListener())
        viewModel.registerSensorEventListener()
        observeLiveDataEvents()
    }

    private fun observeLiveDataEvents() {
        viewModel.run {
            onAccelerometerNotDetected.observe(::getLifecycle) {
                info_text.text = getString(R.string.no_accelerometer_detected)
            }
            onGestureEvent.observe(::getLifecycle) {
                info_text.text = it.name
            }
            onSensorEvent.observe(::getLifecycle) {
                info_text.text = it.name
            }
        }
    }

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        viewModel.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onDestroy() {
        viewModel.unregisterSensorEventListener()
        super.onDestroy()
    }
}