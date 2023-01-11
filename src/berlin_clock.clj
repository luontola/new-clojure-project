(ns berlin-clock
  (:import (java.time LocalTime)))

(def lights-off (repeat \O))

(defn lights [colors n]
  (let [lights-count (count colors)
        lights-on (take n colors)]
    (->> (concat lights-on lights-off)
         (take lights-count)
         (apply str))))

(def mask-1-minutes "YYYY")
(defn time->1-minutes [^LocalTime time]
  (lights mask-1-minutes (mod (.getMinute time) 5)))

(def mask-5-minutes "YYRYYRYYRYY")
(defn time->5-minutes [^LocalTime time]
  (lights mask-5-minutes (int (/ (.getMinute time) 5))))

(def mask-1-hours "RRRR")
(defn time->1-hours [^LocalTime time]
  (lights mask-1-hours (mod (.getHour time) 5)))

(def mask-5-hours "RRRR")
(defn time->5-hours [^LocalTime time]
  (lights mask-5-hours (int (/ (.getHour time) 5))))

(def mask-seconds "Y")
(defn time->seconds [^LocalTime time]
  (lights mask-seconds (mod (inc (.getSecond time)) 2)))

(defn time->berlin-clock [^LocalTime time]
  (str (time->seconds time)
       (time->5-hours time)
       (time->1-hours time)
       (time->5-minutes time)
       (time->1-minutes time)))
