#!/usr/bin/env cake run
(in-ns 'textmate)
(clojure.core/require '[clojure.contrib.repl-utils :as ru])
(clojure.core/require '[clojure.java.io :as io])
(clojure.core/require '[clojure.repl :as repl])
(clojure.core/load-file (clojure.core/str (io/file (cake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))

(defn- raw-symbol-completes [ns symb-prefix target-ns]
  (let [possible (if target-ns
                    (ns-publics target-ns)
                    (concat (ns-publics ns) (ns-refers ns)))]
    (for [symb possible
          :let [name (-> symb first str)]
          :when  (.startsWith name symb-prefix)]
      name)))
    
(defn- raw-ns-completes [ns ns-prefix]
  (for [[alias-ns _] (ns-aliases ns) 
        :let [name (.toString alias-ns)]
        :when (and (.startsWith name ns-prefix))]                  
    name))    
        
(defn- get-completions 
  ([so-far]      
      (let [[_ ns-name var-name] (re-matches #"(?:([\w?_-]+)/)?([\w?_-]*)"  so-far)            
            target-ns (when ns-name ((ns-aliases (file-ns)) (symbol ns-name)))]                    
        #_(println "Complete:" (.toString target-ns))    
        (concat
           (raw-symbol-completes (file-ns) var-name target-ns)
           (when-not target-ns (raw-ns-completes (file-ns) var-name)))))         
  ([] (get-completions (get-symbol-to-autocomplete))))

(println 
  (try
   (string/join " " (get-completions))
   (catch Exception _ "")))

(comment
 (get-completions "repl/s") 
 (ns-resolve)
 (ns-resolve (file-ns) (symbol "get-completions"))
 (ns-aliases (file-ns))
 (ns-refers)
 (doc symbol)
 (.toString ((ns-aliases (file-ns)) (symbol "repl")))
 
 (raw-ns-completes (file-ns) "re")
 (-> get-completions .getClass .getMethods)
 (raw-symbol-completes (file-ns) "re" "")
 (va (second (first (ns-publics (file-ns)))))
 (ns-interns (file-ns))
 (ns-refers (file-ns))
 (ns-map (file-ns))
 (.toString (ffirst (ns-aliases (file-ns))))
)