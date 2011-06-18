#!/usr/bin/env cake run
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])
(require '[clojure.java.io :as io])
(load-file (str (io/file (cake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))

(let [tm-filepath (cake/*env* "TM_FILEPATH")]
  (if (not (= tm-filepath ""))
    (do
      (swap! *compiled-files* conj tm-filepath)
      (try 
        (let [cur-ns (file-ns)]
          (eval-in-ns 'clojure.core 
              (compile cur-ns))
          (clojure.core/println "<pre>Compilation finished.</pre>"))
        (catch Exception e
          (do 
            (stacktrace/print-stack-trace e)
            (clojure.core/println "</pre>")))))
    (clojure.core/println "No file.")))