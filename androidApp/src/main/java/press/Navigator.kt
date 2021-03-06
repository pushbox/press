package press

import android.app.Activity
import android.view.View
import me.saket.press.shared.ui.Navigator
import me.saket.press.shared.ui.ScreenKey
import me.saket.press.shared.ui.ScreenKey.Close

fun View.navigator(): Navigator {
  return Navigator { screen ->
    when (screen) {
      is Close -> findActivity().finish()
      else -> error("Can't navigate to $screen")
    }
  }
}

inline fun <reified T : ScreenKey> Navigator.handle(crossinline handler: (T) -> Unit): Navigator {
  val delegate = this
  return Navigator { screen ->
    when (screen) {
      is T -> handler(screen)
      else -> delegate.lfg(screen)
    }
  }
}

fun View.findActivity(): Activity {
  if (context is Activity) {
    return context as Activity
  } else {
    error("Can't find activity, context: $context")
  }
}
