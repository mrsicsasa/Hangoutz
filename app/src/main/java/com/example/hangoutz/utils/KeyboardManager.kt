import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import dagger.hilt.android.qualifiers.ApplicationContext

class KeyBoardManager(@ApplicationContext private val context: Context) {

    private val activity = context as Activity
    private var keyboardDismissListener: KeyboardDismissListener? = null

    private abstract class KeyboardDismissListener(
        private val rootView: View,
        private val onKeyboardDismiss: () -> Unit
    ) : ViewTreeObserver.OnGlobalLayoutListener {
        private var isKeyboardClosed: Boolean = false
        override fun onGlobalLayout() {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            if (keypadHeight > screenHeight * 0.15) {
                // 0.15 ratio is right enough to determine keypad height.
                isKeyboardClosed = false
            } else if (!isKeyboardClosed) {
                isKeyboardClosed = true
                onKeyboardDismiss.invoke()
            }
        }
    }

    fun attachKeyboardDismissListener(onKeyboardDismiss: () -> Unit) {
        val rootView = activity.findViewById<View>(android.R.id.content)
        keyboardDismissListener = object : KeyboardDismissListener(rootView, onKeyboardDismiss) {}
        keyboardDismissListener?.let {
            rootView.viewTreeObserver.addOnGlobalLayoutListener(it)
        }
    }

    fun releaseKeyboardDismissListener() {
        val rootView = activity.findViewById<View>(android.R.id.content)
        keyboardDismissListener?.let {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(it)
        }
        keyboardDismissListener = null
    }
}