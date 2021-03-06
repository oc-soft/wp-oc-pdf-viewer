
/**
 * setup webpack resolve
 */
class Resolve {


  /**
   * setup configuration
   */
  setup(config) {

    const path = require('path')
    config.resolve = config.resolve || {}
    const resolve = config.resolve
    resolve.alias = resolve.alias || {}
    resolve.alias['dialog-polyfill'] = 'dialog-polyfill/dist/dialog-polyfill.js'

    resolve.alias['media-breaks'] = path.join(
      GradleBuild.config.configDir, 'media-breaks.json')
      


  }
}


((config) => {
  const res = new Resolve()

  res.setup(config)
})(config)

// vi: se ts=2 sw=2 et:
