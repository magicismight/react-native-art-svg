#include "pch.h"
#include "TextView.h"
#include "TextView.g.cpp"

using namespace winrt;
using namespace Microsoft::Graphics::Canvas;
using namespace Microsoft::ReactNative;

namespace winrt::RNSVG::implementation {
void TextView::UpdateProperties(IJSValueReader const &reader, bool forceUpdate, bool invalidate) {
  const JSValueObject &propertyMap = JSValue::ReadObjectFrom(reader);

  for (auto const &pair : propertyMap) {
    auto const &propertyName = pair.first;
    auto const &propertyValue = pair.second;

    if (propertyName == "x") {
      m_x.Clear();
      for (auto &item : propertyValue.AsArray()) {
        m_x.Append(SVGLength::From(item));
      }
    } else if (propertyName == "y") {
      m_y.Clear();
      for (auto &item : propertyValue.AsArray()) {
        m_y.Append(SVGLength::From(item));
      }
    } else if (propertyName == "dx") {
      m_dx.Clear();
      for (auto &item : propertyValue.AsArray()) {
        m_dx.Append(SVGLength::From(item));
      }
    } else if (propertyName == "dy") {
      m_dy.Clear();
      for (auto &item : propertyValue.AsArray()) {
        m_dy.Append(SVGLength::From(item));
      }
    } else if (propertyName == "rotate") {
      m_rotate.Clear();
      for (auto &item : propertyValue.AsArray()) {
        m_rotate.Append(SVGLength::From(item));
      }
    }
  }

  __super::UpdateProperties(reader, forceUpdate, invalidate);
}
} // namespace winrt::RNSVG::implementation
