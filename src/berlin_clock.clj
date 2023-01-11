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

(defn- light-on? [light]
  (not= \O light))

(defn- count-lights [lights]
  (count (filter light-on? lights)))

(defn berlin-clock->time [^String lights]
  (let [[lights-seconds lights] (split-at (count mask-seconds) lights)
        [lights-5-hours lights] (split-at (count mask-5-hours) lights)
        [lights-1-hours lights] (split-at (count mask-1-hours) lights)
        [lights-5-minutes lights] (split-at (count mask-5-minutes) lights)
        [lights-1-minutes _] (split-at (count mask-1-minutes) lights)]
    (LocalTime/of (+ (* 5 (count-lights lights-5-hours))
                     (count-lights lights-1-hours))
                  (+ (* 5 (count-lights lights-5-minutes))
                     (count-lights lights-1-minutes))
                  (case (count-lights lights-seconds)
                    0 1
                    1 0))))
