package org.usfirst.frc.team5809.robot;

public class Destination {

	public enum eFieldSide {
		kLeft, kRight, kUnknown
	};

	public enum eFieldDistance {
		kNear, kMiddle, kFar, kUnknown
	};

	// it may proper to define this in RobotMap
	public class EncoderDistanceMap {
		public static final double kUnknownDistance = 0.0;
		public static final double kNearDistance = 10000.0;
		public static final double kMiddleDistance = 20000.0;
		public static final double kFarDistance = 30000.0;
	}

	private eFieldSide fieldSide;
	private eFieldDistance fieldDistance;

	public Destination() {
		fieldSide = eFieldSide.kUnknown;
		fieldDistance = eFieldDistance.kUnknown;
	}

	public Destination(char position, eFieldDistance distance) {
		if (position == 'L') {
			this.fieldSide = eFieldSide.kLeft;
		} else if (position == 'R') {
			this.fieldSide = eFieldSide.kRight;
		} else {
			this.fieldSide = eFieldSide.kUnknown;
		}

		this.fieldDistance = distance;
	}

	public Destination(eFieldSide side, eFieldDistance distance) {
		this.fieldSide = side;
		this.fieldDistance = distance;
	}

	public eFieldSide getFieldSide() {
		return fieldSide;
	}

	public void setFieldSide(eFieldSide fieldSide) {
		this.fieldSide = fieldSide;
	}

	public eFieldDistance getFieldDistance() {
		return fieldDistance;
	}

	public void setFieldDistance(eFieldDistance fieldDistance) {
		this.fieldDistance = fieldDistance;
	}

	public double getPivotTurnDegrees() {
		switch (this.fieldSide) {
		case kLeft: // desination is on Left, so turn Right
			return -90.0;
		case kRight: // destination is on right, so turn Left
			return 90.0;
		default:
			return 0.0;
		}
	}

	public double getEncoderDistance() {
		switch (this.fieldDistance) {
		case kNear:
			return EncoderDistanceMap.kNearDistance;
		case kMiddle:
			return EncoderDistanceMap.kMiddleDistance;
		case kFar:
			return EncoderDistanceMap.kFarDistance;
		default:
			return EncoderDistanceMap.kUnknownDistance;
		}
	}
}
